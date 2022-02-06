package com.jj.livedatacalladapterfactory.data

import androidx.lifecycle.LiveData
import com.android.example.paging.pagingwithnetwork.reddit.api.TestApi
import com.jj.calladapter.ApiResponse

class MainRepository {

    fun getTest(): LiveData<ApiResponse<TestApi.MyResponse>> {
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