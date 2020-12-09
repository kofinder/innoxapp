package com.finderbar.innox.ui.designer
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentDialogCustomizeDesignerBinding
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class CustomizeDesignDialogFragment: DialogFragment() {

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
        val binding: FragmentDialogCustomizeDesignerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_customize_designer, parent , false)
        var rootView : View  = binding.root
        return rootView
    }

    companion object {
        const val TAG = "CustomizeDesignDialogFragment"
        fun newInstance(body: String, userName: String, userAvatar: String, timeAgo: String): CustomizeDesignDialogFragment {
            val fragment = CustomizeDesignDialogFragment()
            val args = Bundle()
//            args.putString(ARG_KEY_ANSWER_BODY, body)
            fragment.arguments = args
            return fragment
        }
    }
}