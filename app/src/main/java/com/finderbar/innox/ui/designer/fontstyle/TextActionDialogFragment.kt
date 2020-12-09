package com.finderbar.innox.ui.designer.fontstyle

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemColorPickerCallBack
import com.finderbar.innox.ItemFontStyleCallBack
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentDialogCustomizeTextActionBinding
import com.finderbar.innox.repository.OrderItem
import com.finderbar.innox.ui.checkout.ConfirmOrderFragment
import com.finderbar.innox.ui.designer.ColorPickerAdapter
import com.google.android.material.button.MaterialButtonToggleGroup
import java.io.Serializable
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class TextActionDialogFragment: DialogFragment(), MaterialButtonToggleGroup.OnButtonCheckedListener {

    private lateinit var itemColorPickerCallBack: ItemColorPickerCallBack
    private lateinit var itemFontStyleCallBack : ItemFontStyleCallBack

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val window: Window? = dialog?.window
        val layoutParams = window?.attributes
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = 500
        layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams?.height = height.toInt()
        window?.attributes = layoutParams
        window?.attributes!!.gravity = Gravity.BOTTOM
        window.attributes.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        window.setBackgroundDrawableResource(R.drawable.round_border_white)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        window.setWindowAnimations(R.style.DialogAnimation)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding: FragmentDialogCustomizeTextActionBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dialog_customize_text_action,
            parent,
            false
        )
        var rootView : View = binding.root

        val colorPickerColors = mutableListOf<Int>()
        colorPickerColors.add(ContextCompat.getColor(requireContext(), R.color.blue_color_picker))
        colorPickerColors.add(ContextCompat.getColor(requireContext(), R.color.brown_color_picker))
        colorPickerColors.add(ContextCompat.getColor(requireContext(), R.color.green_color_picker))
        colorPickerColors.add(ContextCompat.getColor(requireContext(), R.color.orange_color_picker))
        colorPickerColors.add(ContextCompat.getColor(requireContext(), R.color.red_color_picker))
        colorPickerColors.add(ContextCompat.getColor(requireContext(), R.color.black))
        colorPickerColors.add(
            ContextCompat.getColor(
                requireContext(),
                R.color.red_orange_color_picker
            )
        )
        colorPickerColors.add(
            ContextCompat.getColor(
                requireContext(),
                R.color.sky_blue_color_picker
            )
        )
        colorPickerColors.add(ContextCompat.getColor(requireContext(), R.color.violet_color_picker))
        colorPickerColors.add(ContextCompat.getColor(requireContext(), R.color.white))
        colorPickerColors.add(ContextCompat.getColor(requireContext(), R.color.yellow_color_picker))
        colorPickerColors.add(
            ContextCompat.getColor(
                requireContext(),
                R.color.yellow_green_color_picker
            )
        )

        binding.rvColorPlate.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvColorPlate.setHasFixedSize(true)
        binding.rvColorPlate.adapter = ColorPickerAdapter(
            colorPickerColors,
            itemColorPickerCallBack
        )
        binding.rvColorPlate.isNestedScrollingEnabled = false
        binding.rvColorPlate.itemAnimator = DefaultItemAnimator()
        binding.rvColorPlate.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.btnFontStyle.addOnButtonCheckedListener(this)

        return rootView
    }

    fun setColorPickerListener(itemColorPickerCallBack: ItemColorPickerCallBack) {
        this.itemColorPickerCallBack = itemColorPickerCallBack
    }

    fun setFontStyleListener(itemFontStyleCallBack: ItemFontStyleCallBack) {
        this.itemFontStyleCallBack = itemFontStyleCallBack
    }

    override fun onButtonChecked(
        group: MaterialButtonToggleGroup?,
        checkedId: Int,
        isChecked: Boolean
    ) {
        val isOpen = dialog?.isShowing
        itemFontStyleCallBack.onFontStyleClick(isOpen!!, group, checkedId, isChecked)
    }

    companion object {
        const val TAG = "TextActionDialogFragment"
        fun newInstance(rootView: View, inputText: String, colorCode: Int): TextActionDialogFragment {
            val fragment = TextActionDialogFragment()
            val args = Bundle()
            args.putString("inputText", inputText)
            args.putInt("colorCode", colorCode)
            fragment.arguments = args
            return fragment
        }
    }

}