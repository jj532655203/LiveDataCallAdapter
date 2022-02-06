package com.jj.calladapter

import androidx.lifecycle.LiveData

abstract class RetrofitLiveData<T> : LiveData<T>(), IApiRefresh {
}