package com.finderbar.innox.ui.designer


import android.content.res.ColorStateList
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.finderbar.innox.*
import com.finderbar.innox.databinding.ActivityDesignerTemplateBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.repository.ArtWork
import com.finderbar.innox.repository.CustomItems
import com.finderbar.innox.repository.CustomLayout
import com.finderbar.innox.repository.Font
import com.finderbar.innox.ui.designer.artwork.CustomizeArtWorkDialogFragment
import com.finderbar.innox.ui.designer.fontstyle.CustomizeTextDialogFragment
import com.finderbar.innox.utilities.convertUriToBitmap
import com.finderbar.innox.viewmodel.BizApiViewModel
import com.finderbar.innox.viewmodel.TemplateVM
import com.finderbar.innox.utilities.loadLarge
import com.google.android.material.radiobutton.MaterialRadioButton
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.TextStyleBuilder
import ja.burhanrashid52.photoeditor.ViewType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


class DesignerTemplateActivity: AppCompatActivity(), ItemLayoutButtonClick, OnPhotoEditorListener,
    ItemFontClick, ItemArtWorkCallBack {

    private val bizApiVM: BizApiViewModel by viewModels()
    private val templateVM: TemplateVM by viewModels()
    private lateinit var binding: ActivityDesignerTemplateBinding
    private lateinit var mPhotoEditor: PhotoEditor
    private lateinit var fontFrag: DialogFragment
    private lateinit var artworkFrag: DialogFragment

    private var template: MutableList<CustomItems>? = arrayListOf()
    private var initialTemplate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_designer_template)
        val productId: Int = intent?.extras?.get("productId") as Int

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Create Design"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fontFrag = CustomizeTextDialogFragment()
        (fontFrag as CustomizeTextDialogFragment).setFontListener(this)
        binding.btnText.setOnClickListener{
            fontFrag.show(supportFragmentManager, CustomizeTextDialogFragment.TAG)
        }
        artworkFrag = CustomizeArtWorkDialogFragment()
        (artworkFrag as CustomizeArtWorkDialogFragment).setArtworkListener(this)
        binding.btnArtwork.setOnClickListener{
            artworkFrag.show(supportFragmentManager, CustomizeArtWorkDialogFragment.TAG)
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
                    var defaultCheck = 0;
                    res?.data?.customItems?.forEach { x ->
                        defaultCheck +=1
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
                        if(defaultCheck == 1) {
                            radioButton.isChecked = true
                        }
                    }
                    initialTemplate = true
                    template!!.addAll(res.data?.customItems.copyOf())
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
        val styleBuilder = TextStyleBuilder()
        styleBuilder.withTextColor(R.color.colorPrimary)
        mPhotoEditor.addText(font.name, styleBuilder)
        fontFrag.dismiss()
    }

    override fun onItemClick(artwork: ArtWork) {

        val mainLooper = Looper.getMainLooper()
        GlobalScope.launch {
            val file: File? = convertUriToBitmap(AppContext, Uri.parse(artwork.imageAvatar))
            val filePath = file?.absolutePath
            val bitmap: Bitmap? = BitmapFactory.decodeFile(filePath);
            Handler(mainLooper).post {
                mPhotoEditor.addImage(bitmap)
                artworkFrag.dismiss()
            }
        }
    }



    /**
     * When user long press the existing text this event will trigger implying that user want to
     * edit the current [android.widget.TextView]
     *
     * @param rootView  view on which the long press occurs
     * @param text      current text set on the view
     * @param colorCode current color value set on view
     */
    override fun onEditTextChangeListener(rootView: View?, text: String?, colorCode: Int) {
        print(text)
    }

    /**
     * This is a callback when user adds any view on the [PhotoEditorView] it can be
     * brush,text or sticker i.e bitmap on parent view
     *
     * @param viewType           enum which define type of view is added
     * @param numberOfAddedViews number of views currently added
     * @see ViewType
     */
    override fun onAddViewListener(viewType: ViewType?, numberOfAddedViews: Int) {
        print(viewType)
    }

    /**
     * This is a callback when user remove any view on the [PhotoEditorView] it happens when usually
     * undo and redo happens or text is removed
     *
     * @param viewType           enum which define type of view is added
     * @param numberOfAddedViews number of views currently added
     */
    override fun onRemoveViewListener(viewType: ViewType?, numberOfAddedViews: Int) {
        print(viewType)
    }

    /**
     * A callback when user start dragging a view which can be
     * any of [ViewType]
     *
     * @param viewType enum which define type of view is added
     */
    override fun onStartViewChangeListener(viewType: ViewType?) {
        print(viewType)
    }

    /**
     * A callback when user stop/up touching a view which can be
     * any of [ViewType]
     *
     * @param viewType enum which define type of view is added
     */
    override fun onStopViewChangeListener(viewType: ViewType?) {
        print(viewType)
    }


}