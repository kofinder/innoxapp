package com.finderbar.innox.ui.checkout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentDialogOrderConfirmBinding

class ConfirmOrderFragment: DialogFragment() {

    companion object {
        const val TAG = "ConfirmOrderFragment"
        fun newInstance(): ConfirmOrderFragment {
            val fragment = ConfirmOrderFragment()
            val args = Bundle()
//            args.putString(ARG_KEY_ANSWER_BODY, body)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentDialogOrderConfirmBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_order_confirm, container , false)
        var rootView : View  = binding.root
        return rootView;
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }
}