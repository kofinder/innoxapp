package com.finderbar.innox.ui.instock
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.finderbar.innox.AppConstant.ITEM_PRICE_TEXT
import com.finderbar.innox.AppConstant.ITEM_PRODUCT_ID
import com.finderbar.innox.AppConstant.ITEM_SIZE
import com.finderbar.innox.AppConstant.ITEM_SIZE_ID
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentDialogAddToCartBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.prefs
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
    private var priceText: String? = ""
    private var price: Int? = 0
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
        price = bundle?.getInt(ITEM_PRICE)
        priceText = bundle?.getString(ITEM_PRICE_TEXT)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        val context = activity as AppCompatActivity
        val binding: FragmentDialogAddToCartBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dialog_add_to_cart,
            parent,
            false
        )
        acProgress = ACProgressFlower.Builder(context)
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(android.graphics.Color.WHITE)
            .text("Please Wait")
            .fadeColor(android.graphics.Color.DKGRAY).build();
        binding.txtTitle.text = "$productName ($colorName $sizeName)"
        binding.txtPrice.text = priceText
        binding.txtSubTotal.text = priceText
        binding.txtTotal.text = priceText

        binding.edCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s!!
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var count: Int = 1;
                count = if (s.isNullOrBlank()) {
                    1
                } else {
                    s.toString().toInt();
                }

                val totalCount = count * price!!
                binding.txtTotal.text = "$totalCount Ks"
                binding.txtSubTotal.text = "$totalCount Ks"
            }

            override fun afterTextChanged(s: Editable?) {
                s!!
            }
        })

        binding.btnCart.setOnClickListener{
            val cartText = binding.edCount.text.toString()
            if(cartText.isNullOrBlank()) {
                print(quantity)
                Toasty.error(context, "Please, Input Number of Items!", Toast.LENGTH_SHORT, true).show();
            } else {
                quantity = binding.edCount.text.toString().toInt()
                bizApiVM.loadAddToCart(ShoppingCart(productId!!, 1, 1, quantity)).observe(
                    viewLifecycleOwner,
                    Observer { res ->
                        when (res.status) {
                            Status.LOADING -> {
                                acProgress.show()
                            }
                            Status.SUCCESS -> {
                                acProgress.dismiss()
                                Toasty.success(context, "Success!", Toast.LENGTH_SHORT, true)
                                    .show();
                                dialog?.dismiss()
                                prefs.shoppingCount += 1
                                (activity as InStockProductDetailActivity?)?.setShoppingCartToCount(
                                    prefs.shoppingCount)
                            }
                            Status.ERROR -> {
                                acProgress.dismiss()
                                Toasty.error(context, res.msg.toString(), Toast.LENGTH_SHORT, true)
                                    .show();
                            }
                        }
                    })
            }
        }

        binding.btnCancel.setOnClickListener{ dialog?.dismiss() }

        return binding.root
    }


    companion object {
        const val TAG = "AddToCartDialogFragment"
        fun newInstance(
            productId: Int,
            colorId: Int,
            sizeId: Int,
            productName: String,
            colorName: String,
            sizeName: String,
            price: Int,
            priceText: String
        ): AddToCartDialogFragment {
            val fragment = AddToCartDialogFragment()
            val args = Bundle()
            args.putInt(ITEM_PRODUCT_ID, productId)
            args.putInt(ITEM_COLOR_ID, colorId)
            args.putInt(ITEM_SIZE_ID, sizeId)
            args.putString(ITEM_NAME, productName)
            args.putString(ITEM_COLOR, colorName)
            args.putString(ITEM_SIZE, sizeName)
            args.putInt(ITEM_PRICE, price)
            args.putString(ITEM_PRICE_TEXT, priceText)
            fragment.arguments = args

            return fragment
        }
    }
}