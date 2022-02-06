package com.jj.calladapter

import android.util.Log
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, RetrofitLiveData<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): RetrofitLiveData<ApiResponse<R>> {
        return object : RetrofitLiveData<ApiResponse<R>>() {
            private var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    request(call)
                }
            }

            fun request(call: Call<R>) {
                Log.d("TAG", ": onActive Started ");
                call.enqueue(object : Callback<R> {
                    override fun onResponse(call: Call<R>, response: Response<R>) {
                        Log.d("TAG", ":    $response");
                        postValue(ApiResponse.create(response))
                    }

                    override fun onFailure(call: Call<R>, throwable: Throwable) {
                        Log.d("TAG", ":    ${throwable.localizedMessage}");
                        postValue(ApiResponse.create(throwable))
                    }
                })
            }

            override fun refresh() {
                request(call.clone())
            }
        }
    }
}