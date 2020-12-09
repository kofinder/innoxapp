package com.finderbar.innox.ui.designer
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.finderbar.innox.AppContext
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentDialogCustomAddToCartBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.repository.CustomLayout
import com.finderbar.innox.repository.CustomSize
import com.finderbar.innox.viewmodel.BizApiViewModel
import es.dmoral.toasty.Toasty
import java.util.ArrayList

/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class CustomAddToCartDialogFragment : DialogFragment() {

    private val bizApiVM: BizApiViewModel by viewModels()
    private lateinit var acProgress: ACProgressFlower

    private var productId: Int? = 0
    private var itemId: Int? = 0
    private var colorName: String? = ""
    private var price: Int? = 0
    private var customSizes: MutableList<CustomSize>? = arrayListOf()
    private var customLayouts: MutableList<CustomLayout>? = arrayListOf()

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
        productId = bundle?.getInt("productId")
        itemId = bundle?.getInt("itemId")
        colorName = bundle?.getString("colorName")
        price = bundle?.getInt("price")
        customSizes = bundle?.getParcelableArrayList("customSizes")
        customLayouts = bundle?.getParcelableArrayList("customLayouts")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        val context = activity as AppCompatActivity
        val binding: FragmentDialogCustomAddToCartBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dialog_custom_add_to_cart,
            parent,
            false
        )
        acProgress = ACProgressFlower.Builder(context)
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(android.graphics.Color.WHITE)
            .text("Please Wait")
            .fadeColor(android.graphics.Color.DKGRAY).build();

        val adaptor = CustomAddToCartAdaptor(AppContext, customSizes!!);
        binding.listItem.adapter = adaptor
        setListViewHeight(binding.listItem)

        binding.btnCancel.setOnClickListener{ dialog?.dismiss() }

        return binding.root
    }

    companion object {
        const val TAG = "CustomAddToCartDialogFragment"
        fun newInstance(
            productId: Int,
            itemId: Int,
            customSizes: MutableList<CustomSize>,
            customLayouts:  MutableList<CustomLayout>,
            colorName: String,
            price: Int
        ): CustomAddToCartDialogFragment {
            val fragment = CustomAddToCartDialogFragment()
            val args = Bundle()
            args.putInt("productId", productId)
            args.putInt("itemId", itemId)
            args.putString("colorName", colorName)
            args.putInt("price", price)
            args.putParcelableArrayList("customSizes", customSizes as ArrayList<CustomSize>)
            args.putParcelableArrayList("customLayouts", customLayouts as ArrayList<CustomLayout>)
            fragment.arguments = args
            return fragment
        }
    }

    private fun setListViewHeight(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        val desiredWidth =
            View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.UNSPECIFIED)
        var totalHeight = 0
        var view: View? = null
        for (i in 0 until listAdapter.count) {
            view = listAdapter.getView(i, view, listView)
            if (i == 0) view.layoutParams =
                ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += view.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
    }
}