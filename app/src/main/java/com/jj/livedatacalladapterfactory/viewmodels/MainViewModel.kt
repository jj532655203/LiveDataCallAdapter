package com.jj.livedatacalladapterfactory.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.example.paging.pagingwithnetwork.reddit.api.TestApi
import com.jj.calladapter.ApiResponse
import com.jj.livedatacalladapterfactory.data.MainRepository

class MainViewModel internal constructor(mainRepository: MainRepository): ViewModel() {
    val response:LiveData<ApiResponse<TestApi.MyResponse>> = mainRepository.getTest()
}