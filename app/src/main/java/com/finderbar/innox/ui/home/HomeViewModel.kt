package com.finderbar.innox.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.finderbar.innox.repository.home.Product
import com.finderbar.innox.repository.ApiClient
import com.finderbar.innox.repository.home.HomeRepository
import com.finderbar.innox.repository.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {

    fun loadData() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClient.createService(HomeRepository::class.java)
        val response = api.getPerson()
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    private val products = MutableLiveData<List<Product>>().apply {
        value = listOf(
            Product(
                1,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t1.jpg",
                "Gildan Kids",
                1000
            ),
            Product(
                2,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t2.jpg",
                "Slim Fit",
                800
            ),
            Product(
                3,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t3.jpg",
                "Men's 2-Pack Loose-Fit ",
                7700
            ),
            Product(
                4,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t4.jpg",
                "Short-Sleeve V-Neck Pocket",
                1900
            ),
            Product(
                5,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t5.jpg",
                "Boys' Infant ",
                7800
            ),
            Product(
                6,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t6.jpg",
                "6-Pack Lap-Shoulder Tee",
                8000
            ),
            Product(
                7,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t7.jpg",
                "Men's 2-Pack Slim-Fit",
                89700
            ),
            Product(
                8,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t8.jpg",
                "Short-Sleeve",
                5900
            ),
            Product(
                9,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t9.jpg",
                "Crewneck Stripe",
                40900
            ),
            Product(
                10,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t10.jpg",
                "Women's 2-Pack Slim-Fit",
                69600
            ),
            Product(
                11,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t11.jpg",
                "Short-Sleeve V-Neck",
                40500
            ),
            Product(
                12,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t12.jpg",
                "Men's Big & Tall 2-Pack",
                39400
            ),
            Product(
                16,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t1.jpg",
                "6-Pack Lap-Shoulder Tee",
                30500
            ),
            Product(
                17,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t11.jpg",
                "Boys' Infant",
                6700
            ),
            Product(
                18,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t2.jpg",
                "Women's 2-Pack Classic-Fit",
                9300
            ),
            Product(
                19,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t3.jpg",
                "Men's 2-Pack Slim-Fit",
                2400
            ),
            Product(
                20,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t4.jpg",
                "Short-Sleeve V-Neck Pocket",
                8900
            ),
            Product(
                21,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t5.jpg",
                "Women's 2-Pack Classic-Fit",
                7900
            ),
            Product(
                22,
                "https://finderresources.s3-ap-southeast-1.amazonaws.com/innox/t6.jpg",
                "Women's 2-Pack Classic-Fit",
                86700
            )
        )
    }
    val result: LiveData<List<Product>> = products
}