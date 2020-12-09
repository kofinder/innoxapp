package com.finderbar.innox.ui.instock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class InStockProductFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        const val TAG = "InStockProductFragment"
        fun newInstance(subCategoryId: Int): InStockProductFragment {
            val fragment = InStockProductFragment()
            val args = Bundle()
            args.putInt("subCategoryId", subCategoryId)
            fragment.arguments = args
            return fragment
        }
    }
}