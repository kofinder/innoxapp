package com.finderbar.innox.ui.designer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.finderbar.innox.ItemArtWorkCallBack
import com.finderbar.innox.ItemFontClick
import com.finderbar.innox.ItemLayoutButtonClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityDesignerTemplateBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.repository.ArtWork
import com.finderbar.innox.repository.CustomLayout
import com.finderbar.innox.repository.Font
import com.finderbar.innox.ui.designer.artwork.CustomizeArtWorkDialogFragment
import com.finderbar.innox.ui.designer.fontstyle.CustomizeTextDialogFragment
import com.finderbar.innox.viewmodel.BizApiViewModel
import com.finderbar.innox.viewmodel.TemplateVM
import com.finderbar.jovian.utilities.android.convertUriToBitmap
import com.finderbar.jovian.utilities.android.loadLarge
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.TextStyleBuilder
import ja.burhanrashid52.photoeditor.ViewType


class DesignerTemplateActivity: AppCompatActivity(), ItemLayoutButtonClick, OnPhotoEditorListener,
    ItemFontClick, ItemArtWorkCallBack {

    private val bizApiVM: BizApiViewModel by viewModels()
    private val templateVM: TemplateVM by viewModels()
    private lateinit var binding: ActivityDesignerTemplateBinding
    private lateinit var mPhotoEditor: PhotoEditor
    private lateinit var fontFrag: DialogFragment
    private lateinit var artworkFrag: DialogFragment

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
        var bitmap = Glide.with(this)
            .asBitmap()
            .load(Uri.parse(artwork.imageAvatar))
            .submit().get()

        mPhotoEditor.addImage(bitmap)
        artworkFrag.dismiss()
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