/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.paging.pagingwithnetwork.reddit.api

import android.util.Log
import androidx.lifecycle.LiveData
import com.jj.calladapter.ApiResponse
import com.jj.calladapter.LiveDataCallAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * API communication setup
 */
interface TestApi {

    @GET("app/pad/getTodayTasksByAccountId?accountId=31588&padMachineId=7013T1100144F")
    fun getTest(): LiveData<ApiResponse<MyResponse>>

    class MyResponse(val code: String) {
        override fun toString(): String {
            return "MyResponse(code='$code')"
        }
    }


    companion object {
        private const val BASE_URL = "https://test-all.eebbk.net/homework-center/"
        fun create(): TestApi {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API", it + Thread.currentThread().name)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(HttpUrl.parse(BASE_URL)!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(TestApi::class.java)
        }
    }
}