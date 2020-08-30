package com.finderbar.innox.ui.instock
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentDialogAddToCartBinding
import com.finderbar.innox.ui.checkout.ProductCheckoutActivity

class AddToCartDialogFragment : DialogFragment() {

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
//        if (bundle != null) {
//            body = bundle.getString(ARG_KEY_ANSWER_BODY)
//            userName = bundle.getString(ARG_KEY_USER_NAME)
//            userAvatar = bundle.getString(ARG_KEY_USER_AVATAR)
//            timeAgo = bundle.getString(ARG_KEY_TIME_AGO)
//        }
    }


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        val binding: FragmentDialogAddToCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_add_to_cart, parent , false)
        var rootView : View  = binding.root
        binding.btnCart.setOnClickListener{startActivity(Intent(activity, ProductCheckoutActivity::class.java))}
        return rootView
    }

    companion object {
        const val TAG = "AddToCartDialogFragment"
        fun newInstance(body: String, userName: String, userAvatar: String, timeAgo: String): AddToCartDialogFragment {
            val fragment =
                AddToCartDialogFragment()
            val args = Bundle()
//            args.putString(ARG_KEY_ANSWER_BODY, body)
            fragment.arguments = args
            return fragment
        }
    }
}