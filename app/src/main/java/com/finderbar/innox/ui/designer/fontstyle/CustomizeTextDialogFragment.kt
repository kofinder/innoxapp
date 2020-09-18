package com.finderbar.innox.ui.designer.fontstyle
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemFontClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentDialogCustomizeDesignerBinding
import com.finderbar.innox.databinding.FragmentDialogCustomizeTextDesignerBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.repository.Font
import com.finderbar.innox.ui.designer.artwork.ArtWorkDesignerAdaptor
import com.finderbar.innox.utilities.SpaceItemDecoration
import com.finderbar.innox.viewmodel.BizApiViewModel

class CustomizeTextDialogFragment: DialogFragment(), ItemFontClick {

    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val window: Window? = dialog?.window
        val layoutParams = window?.attributes
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels / 1.5
        layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams?.height = height.toInt()
        window?.attributes = layoutParams
        window?.attributes!!.gravity = Gravity.BOTTOM
        window?.attributes.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        window?.setBackgroundDrawableResource(R.drawable.round_border_white)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        window?.setWindowAnimations(R.style.DialogAnimation)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        val binding: FragmentDialogCustomizeTextDesignerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_customize_text_designer, parent , false)
        var rootView : View  = binding.root

        bizApiVM.loadFont().observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print(res.status)
                }
                Status.SUCCESS -> {
                    val adaptor =  FontAdaptor(res.data?.fonts!!, this)
                    binding.recyclerView.addItemDecoration(SpaceItemDecoration(10))
                    val layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.recyclerView.layoutManager = layoutManager
                    binding.recyclerView.adapter = adaptor
                    binding.recyclerView.setHasFixedSize(true)
                    binding.recyclerView.itemAnimator = DefaultItemAnimator()
                    binding.recyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool())
                }
                Status.ERROR -> {
                    print(res.msg)
                }
            }
        })

        return rootView
    }

    companion object {
        const val TAG = "CustomizeTextDialogFragment"
//        fun newInstance(body: String): CustomizeTextDialogFragment {
//            val fragment =
//                CustomizeTextDialogFragment()
//            val args = Bundle()
//            fragment.arguments = args
//            return fragment
//        }
    }

    override fun onItemClick(font: Font) {
        print(font)
    }
}