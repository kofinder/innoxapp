package com.finderbar.innox.ui.product
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.finderbar.innox.R
import de.hdodenhof.circleimageview.CircleImageView

class AddToCartDialogFragment : DialogFragment() {
//    private var txtBody: TextView? = null;
//    private var txtUserName: TextView? = null;
//    private var imgAvatar: CircleImageView? = null;
//    private var txtTimeAgo: TextView? = null
//
//    private var body: String? = null
//    private var userName: String? = null
//    private var userAvatar: String? = null;
//    private var timeAgo: String? = null

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
        val view =  inflater.inflate(R.layout.fragment_dialog_add_to_cart, parent, false)
//        txtUserName = view.findViewById(R.id.txt_usr_name)
//        imgAvatar = view.findViewById(R.id.user_image)
//        txtBody = view.findViewById(R.id.body)
//        txtTimeAgo = view.findViewById(R.id.txt_ago)
//        // set initial value
//        txtBody?.setMarkdown(body!!)
//        txtUserName?.text = userName
//        imgAvatar?.loadAvatar(Uri.parse(userAvatar))
//        txtTimeAgo?.text = agoTimeUtil(timeAgo!!)

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
//        txtUserName = null
//        imgAvatar = null
//        txtBody = null
    }

    companion object {
        const val TAG = "AddToCartDialogFragment"
        fun newInstance(body: String, userName: String, userAvatar: String, timeAgo: String): AddToCartDialogFragment {
            val fragment = AddToCartDialogFragment()
//            val args = Bundle()
//            args.putString(ARG_KEY_ANSWER_BODY, body)
//            args.putString(ARG_KEY_USER_NAME, userName)
//            args.putString(ARG_KEY_USER_AVATAR, userAvatar)
//            args.putString(ARG_KEY_TIME_AGO, timeAgo)
//            fragment.arguments = args
            return fragment
        }
    }
}