package com.jj.livedatacalladapterfactory.data

import com.android.example.paging.pagingwithnetwork.reddit.api.TestApi
import com.jj.calladapter.ApiResponse
import com.jj.calladapter.RetrofitLiveData

class MainRepository {

    fun getTest(): RetrofitLiveData<ApiResponse<TestApi.MyResponse>> {
        return TestApi.create().getTest()
    }

    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: MainRepository().also { instance = it }
        }

    }
}