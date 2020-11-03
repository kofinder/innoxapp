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
import com.finderbar.innox.*
import com.finderbar.innox.AppConstant.ART_WORK_CATEGORY_ID
import com.finderbar.innox.AppConstant.ART_WORK_CATEGORY_TITLE
import com.finderbar.innox.databinding.FragmentArtworkBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.utilities.SpaceItemDecoration
import com.finderbar.innox.viewmodel.BizApiViewModel

class CategoryArtWorkFragment: Fragment() {

    private val bizApiVM: BizApiViewModel by viewModels()
    private lateinit var fragCallBack: RootFragListener
    private lateinit var itemArtworkCallBack: ItemArtWorkCallBack
    private var categoryId: Int = 0
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bundle = savedInstanceState
        if (bundle == null) bundle = arguments
        categoryId = bundle?.getInt(ART_WORK_CATEGORY_ID)!!
        title = bundle?.getString(ART_WORK_CATEGORY_TITLE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentArtworkBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_artwork, container , false)
        var rootView : View = binding.root
        fragCallBack = parentFragment as RootFragListener
        itemArtworkCallBack = activity as ItemArtWorkCallBack

        binding.txtTitle.text = "$title ArtWork "
        binding.btnBack.setOnClickListener{fragCallBack.onBackPressed()}

        bizApiVM.loadArtWorkByCategoryId(categoryId).observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print(res.status)
                }
                Status.SUCCESS -> {
                    val adaptor =
                        ArtWorkAdaptor(
                            res.data?.artWorks!!,
                            itemArtworkCallBack
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


    companion object {
        const val TAG = "CategoryArtWorkFragment"
        fun newInstance(categoryId: Int, title: String): CategoryArtWorkFragment {
            val fragment =
                CategoryArtWorkFragment()
            val args = Bundle()
            fragment.arguments = args
            args.putInt(ART_WORK_CATEGORY_ID, categoryId)
            args.putString(ART_WORK_CATEGORY_TITLE, title)
            return fragment
        }
    }
}