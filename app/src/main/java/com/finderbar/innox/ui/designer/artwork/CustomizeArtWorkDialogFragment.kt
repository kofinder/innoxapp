package com.finderbar.innox.ui.designer.artwork
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.finderbar.innox.R
import com.finderbar.innox.RootFragListener
import com.finderbar.innox.databinding.FragmentDialogCustomizeArtworkBinding

class CustomizeArtWorkDialogFragment: DialogFragment(), RootFragListener {

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
        val binding: FragmentDialogCustomizeArtworkBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_customize_artwork, parent , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var ft : FragmentTransaction = childFragmentManager.beginTransaction()
        ft.replace(R.id.ft_main, ArtWorkCustomizeFragment() )
        ft.commit()
    }

    companion object {
        const val TAG = "CustomizeArtWorkDialogFragment"
    }

    override fun onPressed(frag: Fragment) {
        var ft : FragmentTransaction = childFragmentManager.beginTransaction()
        ft.replace(R.id.ft_main, frag)
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun onBackPressed() {
        var ft : FragmentTransaction = childFragmentManager.beginTransaction()
        ft.replace(R.id.ft_main, ArtWorkCustomizeFragment())
        ft.addToBackStack(ArtWorkCustomizeFragment.TAG)
        ft.commit()
    }
}