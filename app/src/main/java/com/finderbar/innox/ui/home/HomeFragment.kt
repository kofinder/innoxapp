package com.finderbar.innox.ui.home

import android.content.Intent
import android.graphics.Color
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentHomeBinding
import com.finderbar.innox.repository.Status
import com.finderbar.innox.ui.designer.CustomizeDesignListActivity
import com.finderbar.innox.ui.instock.InstockProductDetailActivity
import com.finderbar.innox.utilities.SpaceItemDecoration
import com.finderbar.innox.viewmodel.BaseApiViewModel
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment() , ItemProductClick {

    private val baseApiVM: BaseApiViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container , false)
        var rootView : View  = binding.root

        val sliderAdapter = ImageSlideAdaptor(arrayListOf());
        binding.imageSlider.sliderAdapter = sliderAdapter
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM)
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.imageSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        binding.imageSlider.indicatorSelectedColor = Color.WHITE
        binding.imageSlider.indicatorUnselectedColor = Color.GRAY
        binding.imageSlider.scrollTimeInSec = 4
        binding.imageSlider.startAutoCycle()

        val categoryAdaptor = CategoryProductAdaptor(arrayListOf())
        binding.rvCategoryProduct.addItemDecoration(SpaceItemDecoration(5));
        binding.rvCategoryProduct.layoutManager = GridLayoutManager(requireContext(), 4);
        binding.rvCategoryProduct.adapter = categoryAdaptor

        val popularAdaptor = PopularProductAdaptor(arrayListOf(), this)
        binding.rvPopularProduct.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopularProduct.setHasFixedSize(true);
        binding.rvPopularProduct.adapter = popularAdaptor
        binding.rvPopularProduct.isNestedScrollingEnabled = false
        binding.rvPopularProduct.itemAnimator = DefaultItemAnimator()
        binding.rvPopularProduct.setRecycledViewPool(RecyclerView.RecycledViewPool());

        val promotionAdaptor = PromotionProductAdaptor(arrayListOf(), this)
        binding.rvPromotionProduct.addItemDecoration(SpaceItemDecoration(10));
        binding.rvPromotionProduct.layoutManager = GridLayoutManager(requireContext(), 2);
        binding.rvPromotionProduct.adapter = promotionAdaptor
        binding.rvPromotionProduct.isNestedScrollingEnabled = false
        binding.rvPromotionProduct.itemAnimator = DefaultItemAnimator()
        binding.rvPromotionProduct.setRecycledViewPool(RecyclerView.RecycledViewPool());

        baseApiVM.loadData().observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print(res.status)
                }
                Status.SUCCESS -> {
                    sliderAdapter.addAll(res.data?.banners!!)
                    categoryAdaptor.addAll(res.data?.categories!!)
                    popularAdaptor.addAll(res.data?.popularProducts!!)
                    promotionAdaptor.addAll(res.data?.promotionProducts!!)
                }
                Status.ERROR -> {
                    print(res.msg)
                }
            }
        })

        binding.btnMoreCustom.setOnClickListener{startActivity(Intent(activity, CustomizeDesignListActivity::class.java))}

        return rootView;
    }

    override fun onItemClick(_id: Int, position: Int) {
        val intent = Intent(activity, InstockProductDetailActivity::class.java)
        intent.putExtra("_id", _id)
        intent.putExtra("position", position)
        startActivity(intent)
    }
}
