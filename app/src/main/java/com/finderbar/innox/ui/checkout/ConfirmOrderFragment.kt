package com.finderbar.innox.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentDialogOrderConfirmBinding
import com.finderbar.innox.repository.OrderItem
import java.io.Serializable

class ConfirmOrderFragment: DialogFragment() {
    private var orderItem: ArrayList<OrderItem>? = arrayListOf()
    private var invoiceNumber: String? = ""
    private var totalCost: String? = ""
    private var deliveryCost: String? = ""

    companion object {
        const val TAG = "ConfirmOrderFragment"
        fun newInstance(orderItem: ArrayList<OrderItem>, invoiceNumber: String, totalCost: String, deliveryCost: String): ConfirmOrderFragment {
            val fragment = ConfirmOrderFragment()
            val args = Bundle()
            args.putSerializable("orderItem", orderItem as Serializable)
            args.putString("invoiceNumber", invoiceNumber)
            args.putString("totalCost", totalCost)
            args.putString("deliveryCost", deliveryCost)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        var bundle = savedInstanceState
        if (bundle == null) bundle = arguments
        orderItem = arguments?.getSerializable("orderItem") as ArrayList<OrderItem>
        invoiceNumber = bundle?.getString("invoiceNumber")
        totalCost = bundle?.getString("totalCost")
        deliveryCost = bundle?.getString("deliveryCost")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentDialogOrderConfirmBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_order_confirm, container , false)

        print(orderItem)


        return binding.root;
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