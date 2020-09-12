package com.finderbar.innox.ui.designer.artwork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.AppConstant.ART_WORK_DESIGNER_ID
import com.finderbar.innox.AppConstant.ART_WORK_DESIGNER_TITLE
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.R
import com.finderbar.innox.RootFragListener
import com.finderbar.innox.databinding.FragmentArtworkBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.utilities.SpaceItemDecoration
import com.finderbar.innox.viewmodel.BizApiViewModel

class DesignerArtWorkFragment: Fragment(), ItemProductClick {
    private val bizApiVM: BizApiViewModel by viewModels()
    private lateinit var fragCallBack: RootFragListener
    private var designerId: Int = 0
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bundle = savedInstanceState
        if (bundle == null) bundle = arguments
        designerId = bundle?.getInt(ART_WORK_DESIGNER_ID)!!
        title = bundle?.getString(ART_WORK_DESIGNER_TITLE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentArtworkBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_artwork, container , false)
        var rootView : View = binding.root
        fragCallBack = parentFragment as RootFragListener

        binding.txtTitle.text = "ArtWork By $title"
        binding.btnBack.setOnClickListener{fragCallBack.onBackPressed()}

        bizApiVM.loadArtWorkByDesignerId(designerId).observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print(res.status)
                }
                Status.SUCCESS -> {
                    val adaptor =
                        ArtWorkAdaptor(
                            res.data?.artWork!!,
                            this
                        )
                    binding.recyclerView.addItemDecoration(SpaceItemDecoration(5))
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

    override fun onItemClick(_id: Int, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val TAG = "DesignerArtWorkFragment"
        fun newInstance(designerId: Int, title: String): DesignerArtWorkFragment {
            val fragment =
                DesignerArtWorkFragment()
            val args = Bundle()
            args.putInt(ART_WORK_DESIGNER_ID, designerId)
            args.putString(ART_WORK_DESIGNER_TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }
}