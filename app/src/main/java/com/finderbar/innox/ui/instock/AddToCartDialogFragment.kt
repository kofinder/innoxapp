package com.finderbar.innox.ui.instock
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.finderbar.innox.AppConstant.ITEM_COLOR
import com.finderbar.innox.AppConstant.ITEM_COLOR_ID
import com.finderbar.innox.AppConstant.ITEM_NAME
import com.finderbar.innox.AppConstant.ITEM_PRICE
import com.finderbar.innox.AppConstant.ITEM_PRODUCT_ID
import com.finderbar.innox.AppConstant.ITEM_SIZE
import com.finderbar.innox.AppConstant.ITEM_SIZE_ID
import com.finderbar.innox.AppContext
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentDialogAddToCartBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.prefs
import com.finderbar.innox.repository.Register
import com.finderbar.innox.repository.ShoppingCart
import com.finderbar.innox.ui.MainActivity
import com.finderbar.innox.viewmodel.BizApiViewModel
import es.dmoral.toasty.Toasty

class AddToCartDialogFragment : DialogFragment() {
    private val bizApiVM: BizApiViewModel by viewModels()
    private lateinit var acProgress: ACProgressFlower

    private var productId: Int? = 0
    private var colorId: Int? = 0
    private var sizeId: Int? = 0
    private var productName: String? = ""
    private var colorName: String? = ""
    private var sizeName: String? = ""
    private var price: String? = ""
    private var quantity: Int = 1

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
        productId = bundle?.getInt(ITEM_PRODUCT_ID)
        colorId = bundle?.getInt(ITEM_COLOR_ID)
        sizeId= bundle?.getInt(ITEM_SIZE_ID)
        productName = bundle?.getString(ITEM_NAME)
        colorName = bundle?.getString(ITEM_COLOR)
        sizeName = bundle?.getString(ITEM_SIZE)
        price = bundle?.getString(ITEM_PRICE)
    }


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        val context = activity as AppCompatActivity
        val binding: FragmentDialogAddToCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_add_to_cart, parent , false)
        acProgress = ACProgressFlower.Builder(context)
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(android.graphics.Color.WHITE)
            .text("Please Wait")
            .fadeColor(android.graphics.Color.DKGRAY).build();
        binding.txtTitle.text = "$productName ($colorName $sizeName)"
        binding.txtPrice.text = price
        binding.txtSubTotal.text = price
        binding.txtTotal.text = price

        binding.btnCart.setOnClickListener{
            quantity = binding.edCount.text.toString().toInt()
            bizApiVM.loadAddToCart(ShoppingCart(productId!!, 1, 1, quantity)).observe(viewLifecycleOwner, Observer { res ->
                when (res.status) {
                    Status.LOADING -> {
                        acProgress.show()
                    }
                    Status.SUCCESS -> {
                        acProgress.dismiss()
                        Toasty.success(context, "Success!", Toast.LENGTH_SHORT, true).show();
                        val intent = Intent(AppContext, MainActivity::class.java)
                        startActivity(intent)
                    }
                    Status.ERROR -> {
                        acProgress.dismiss()
                        Toasty.error(context, res.msg.toString(), Toast.LENGTH_SHORT, true).show();
                    }
                }
            })
        }

        binding.btnCancel.setOnClickListener{ dialog?.dismiss() }

        return binding.root
    }


    companion object {
        const val TAG = "AddToCartDialogFragment"
        fun newInstance(productId: Int, colorId: Int, sizeId: Int, productName: String, colorName: String, sizeName: String, price: String): AddToCartDialogFragment {
            val fragment = AddToCartDialogFragment()
            val args = Bundle()
            args.putInt(ITEM_PRODUCT_ID, productId)
            args.putInt(ITEM_COLOR_ID, colorId)
            args.putInt(ITEM_SIZE_ID, sizeId)
            args.putString(ITEM_NAME, productName)
            args.putString(ITEM_COLOR, colorName)
            args.putString(ITEM_SIZE, sizeName)
            args.putString(ITEM_PRICE, price)
            fragment.arguments = args

            return fragment
        }
    }
}