package com.finderbar.innox.ui.designer.fontstyle
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentDialogCustomizeDesignerBinding

class CustomizeTextDialogFragment: DialogFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val window: Window? = dialog?.window
        val layoutParams = window?.attributes
        layoutParams!!.width = ViewGroup.LayoutParams.MATCH_PARENT
        window?.attributes = layoutParams
        window?.attributes!!.gravity = Gravity.BOTTOM;
        window?.attributes.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        window?.setBackgroundDrawableResource(R.drawable.round_border_white)
        window?.setWindowAnimations(R.style.DialogAnimation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bundle = savedInstanceState
        if (bundle == null) bundle = arguments
    }


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        val binding: FragmentDialogCustomizeDesignerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_customize_text_designer, parent , false)
        var rootView : View  = binding.root
        return rootView
    }

    companion object {
        const val TAG = "CustomizeTextDialogFragment"
        fun newInstance(body: String, userName: String, userAvatar: String, timeAgo: String): CustomizeTextDialogFragment {
            val fragment =
                CustomizeTextDialogFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}