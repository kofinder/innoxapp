package com.finderbar.innox.ui.designer
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentDialogCustomizeArtworkBinding
import com.finderbar.innox.utilities.ViewPagerAdapter

class CustomizeArtWorkDialogFragment: DialogFragment() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bundle = savedInstanceState
        if (bundle == null) bundle = arguments
    }


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        val binding: FragmentDialogCustomizeArtworkBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_customize_artwork, parent , false)
        var rootView : View  = binding.root

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ArtWorkCategoryFragment(), "ArtWork By Category")
        adapter.addFragment(ArtWorkDesignerFragment(), "ArtWork By Designer")
        binding.viewPager.adapter = adapter
        binding.tabhost.setupWithViewPager(binding.viewPager)

        return rootView
    }

    companion object {
        const val TAG = "CustomizeArtWorkDialogFragment"
        fun newInstance(body: String, userName: String, userAvatar: String, timeAgo: String): CustomizeArtWorkDialogFragment {
            val fragment = CustomizeArtWorkDialogFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}