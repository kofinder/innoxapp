package com.finderbar.innox.ui.designer


import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.*
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.finderbar.innox.*
import com.finderbar.innox.AppConstant.FONT_BOOKS
import com.finderbar.innox.databinding.ActivityDesignerTemplateBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.repository.ArtWork
import com.finderbar.innox.repository.CustomItems
import com.finderbar.innox.repository.CustomLayout
import com.finderbar.innox.repository.Font
import com.finderbar.innox.ui.designer.artwork.CustomizeArtWorkDialogFragment
import com.finderbar.innox.ui.designer.fontstyle.CustomizeTextDialogFragment
import com.finderbar.innox.ui.designer.fontstyle.TextActionDialogFragment
import com.finderbar.innox.utilities.convertUriToBitmap
import com.finderbar.innox.utilities.loadLarge
import com.finderbar.innox.viewmodel.BizApiViewModel
import com.finderbar.innox.viewmodel.TemplateVM
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.radiobutton.MaterialRadioButton
import es.dmoral.toasty.Toasty
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.ViewType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class DesignerTemplateActivity: AppCompatActivity(), ItemLayoutButtonClick, OnPhotoEditorListener,
    ItemFontClick, ItemArtWorkCallBack {

    private val bizApiVM: BizApiViewModel by viewModels()
    private val templateVM: TemplateVM by viewModels()
    private lateinit var binding: ActivityDesignerTemplateBinding
    private lateinit var mPhotoEditor: PhotoEditor
    private lateinit var fontFrag: DialogFragment
    private lateinit var artworkFrag: DialogFragment
    private lateinit var acProgress: ACProgressFlower

    private var template: MutableList<CustomItems>? = arrayListOf()
    private var initialTemplate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_designer_template)
        val productId: Int = intent?.extras?.get("productId") as Int

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Create Design"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        acProgress = ACProgressFlower.Builder(this)
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(android.graphics.Color.WHITE)
            .text("Please Wait")
            .fadeColor(Color.DKGRAY).build()

        fontFrag = CustomizeTextDialogFragment()
        (fontFrag as CustomizeTextDialogFragment).setFontListener(this)
        binding.btnText.setOnClickListener{
            fontFrag.show(supportFragmentManager, fontFrag.tag)
        }
        artworkFrag = CustomizeArtWorkDialogFragment()
        (artworkFrag as CustomizeArtWorkDialogFragment).setArtworkListener(this)
        binding.btnArtwork.setOnClickListener{
            artworkFrag.show(supportFragmentManager, artworkFrag.tag)
        }

        mPhotoEditor = PhotoEditor.Builder(this, binding.imgDesigner)
            .setPinchTextScalable(true)
            .build()
        mPhotoEditor.setOnPhotoEditorListener(this)

        loadTemplate(productId)

        templateVM.items?.observe(this, Observer { x ->
            val adaptor = ButtonGroupAdaptor(x.customLayout!!, this)
            binding.recyclerView.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.adapter = adaptor
            binding.recyclerView.itemAnimator = DefaultItemAnimator()
            templateVM.getLayout(0, x.customLayout)
        })

        templateVM.layouts?.observe(this, Observer { x ->
            binding.imgDesigner.source.loadLarge(Uri.parse(x.imageAvatar))
        })

        binding.rdoGroup.setOnCheckedChangeListener { _, optId ->
            if(!initialTemplate) {
                initialTemplate = true
            } else {
                templateVM.getTemplate(optId, template!!)
            }
        }


        binding.btnSave.setOnClickListener {
            acProgress.show()
            val bitmap = Bitmap.createBitmap(
                binding.lblTemplate.width,
                binding.lblTemplate.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            binding.lblTemplate.draw(canvas)

            var outputStream: FileOutputStream? = null
            val sdCard = Environment.getExternalStorageDirectory()
            val directory = File(sdCard.absolutePath + "/innox")
            directory.mkdir()
            val fileName = String.format("%d.png", System.currentTimeMillis())
            val outFile = File(directory, fileName)
            Toasty.success(this, "Image Saved Successfully").show()
            try {
                outputStream = FileOutputStream(outFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
                val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                intent.data = Uri.fromFile(outFile)
                sendBroadcast(intent)
                acProgress.dismiss()
            } catch (ex: FileNotFoundException) {
                ex.printStackTrace()
                ex.message?.let { it1 -> Toasty.error(this, it1).show() }
                acProgress.dismiss()
            } catch (ex: IOException) {
                ex.printStackTrace()
                ex.message?.let { it1 -> Toasty.error(this, it1).show() }
                acProgress.dismiss()
            }
        }

        binding.btnCart.setOnClickListener {
            Toasty.info(this, "custom product uploaded").show()
        }

        if(!fontFrag.isAdded) {
            return
        }
        if(!artworkFrag.isAdded) {
            return
        }
    }
    private fun loadTemplate(productId: Int) {
        bizApiVM.loadDesignerProduct(productId).observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print("loading")
                }
                Status.SUCCESS -> {
                    supportActionBar?.title = res.data?.name
                    binding.txtPrice.text = res.data?.priceText
                    templateVM.getTemplate(0, res.data?.customItems!!)
                    var defaultCheck = 0
                    res?.data.customItems.forEach { x ->
                        defaultCheck += 1
                        val radioButton = MaterialRadioButton(this)
                        radioButton.id = x.colorId
                        radioButton.tag = x.colorId
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            radioButton.setCircleColor(Color.parseColor("#" + x.colorCode))
                        }
                        radioButton.scaleX = 1.5f
                        radioButton.scaleY = 1.5f
                        val params = RadioGroup.LayoutParams(
                            RadioGroup.LayoutParams.WRAP_CONTENT,
                            RadioGroup.LayoutParams.WRAP_CONTENT
                        )

                        binding.rdoGroup.addView(radioButton, params)
                        if (defaultCheck == 1) {
                            radioButton.isChecked = true
                        }
                    }
                    initialTemplate = true
                    template!!.addAll(res.data.customItems.copyOf())
                }
                Status.ERROR -> {
                    print("err")
                }
            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClick(_id: Int, arrays: MutableList<CustomLayout>) {
        templateVM.getLayout(_id, arrays)
    }

    override fun onItemClick(font: Font) {
        val typeface = Typeface.createFromAsset(assets, FONT_BOOKS["firasans"])
        mPhotoEditor.addText(typeface, font.name, R.color.colorBlack)
        fontFrag.dismiss()
    }


    override fun onItemClick(artwork: ArtWork) {
        val mainLooper = Looper.getMainLooper()
        GlobalScope.launch {
            val file: File? = convertUriToBitmap(AppContext, Uri.parse(artwork.imageAvatar))
            val filePath = file?.absolutePath
            val bitmap: Bitmap? = BitmapFactory.decodeFile(filePath)
            Handler(mainLooper).post {
                mPhotoEditor.addImage(bitmap)
                artworkFrag.dismiss()
            }
        }
    }


    override fun onEditTextChangeListener(rootView: View?, text: String?, colorCode: Int) {
        val frag = TextActionDialogFragment.newInstance(rootView!!, text!!, colorCode);
        frag.show(supportFragmentManager, frag.tag)
        frag.setColorPickerListener(object : ItemColorPickerCallBack {
            override fun onColorPickerClick(colorCode: Int) {
                mPhotoEditor.editText(rootView, text, colorCode)
                frag.dismiss()
            }
        })
        frag.setFontStyleListener(object : ItemFontStyleCallBack {
            override fun onFontStyleClick(
                isOpen: Boolean,
                group: MaterialButtonToggleGroup?,
                checkedId: Int,
                isChecked: Boolean
            ) {
                var typeface = Typeface.createFromAsset(assets, FONT_BOOKS["firasans"])
                mPhotoEditor.editText(rootView, typeface, text, colorCode)
                var textView: TextView = rootView.findViewById(ja.burhanrashid52.photoeditor.R.id.tvPhotoEditorText)
                when (checkedId) {
                    R.id.btn_normal -> {
                        textView.setTypeface(typeface, Typeface.NORMAL)
                    }
                    R.id.btn_italic -> {
                        textView.setTypeface(typeface, Typeface.ITALIC)
                    }
                    R.id.btn_bold -> {
                        textView.setTypeface(typeface, Typeface.BOLD)
                    }
                    R.id.btn_bold_italic -> {
                        textView.setTypeface(typeface, Typeface.BOLD_ITALIC)
                    }
                }
                frag.dismiss()
            }
        })
    }

    override fun onAddViewListener(viewType: ViewType?, numberOfAddedViews: Int) {
        print(viewType)
    }

    override fun onRemoveViewListener(viewType: ViewType?, numberOfAddedViews: Int) {
        print(viewType)
    }
    override fun onStartViewChangeListener(viewType: ViewType?) {
        print(viewType)
    }
    override fun onStopViewChangeListener(viewType: ViewType?) {
        print(viewType)
    }

}