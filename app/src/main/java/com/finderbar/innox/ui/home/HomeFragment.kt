package com.finderbar.innox.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.finderbar.innox.R
import com.finderbar.innox.ui.account.LoginActivity
import com.finderbar.innox.ui.designer.CustomizeDesignActivity
import com.google.android.material.button.MaterialButton
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var unkinder: Unbinder

    @BindView(R.id.btn_more_custom) lateinit var designerButton: MaterialButton
    @BindView(R.id.btn_more_instock) lateinit var instockButton: MaterialButton

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        unkinder = ButterKnife.bind(this, root)

        val sliderView: SliderView = root.findViewById(R.id.imageSlider)

        val adaptor: ImageSlideAdaptor = ImageSlideAdaptor(requireActivity());
        sliderView.sliderAdapter = adaptor

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        sliderView.indicatorSelectedColor = Color.WHITE
        sliderView.indicatorUnselectedColor = Color.GRAY
        sliderView.scrollTimeInSec = 4
        sliderView.startAutoCycle()

        val productAdaptor: ProductAdaptor = ProductAdaptor(arrayListOf())
        val recyclerView: RecyclerView = root.findViewById(R.id.rv_product)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true)
        recyclerView.adapter = productAdaptor

        homeViewModel.result.observe(viewLifecycleOwner, Observer {
            productAdaptor.addAll(it)
        })

        designerButton.setOnClickListener{startActivity(Intent(activity, CustomizeDesignActivity::class.java))}

        return root;
    }
}
