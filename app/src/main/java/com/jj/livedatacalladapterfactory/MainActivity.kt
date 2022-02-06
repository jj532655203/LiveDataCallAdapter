package com.jj.livedatacalladapterfactory

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jj.calladapter.IApiRefresh
import com.jj.livedatacalladapterfactory.utils.InjectorUtils
import com.jj.livedatacalladapterfactory.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private var mTestRefresh: IApiRefresh? = null

    private val viewModel: MainViewModel by viewModels {
        InjectorUtils.provideMainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.tv)

        viewModel.testLiveData.observe(this) { result ->
            Log.d("MainActivity", "$result")
            tv.setText(result.toString())
        }

        mTestRefresh = viewModel.testLiveData
        tv.setOnClickListener {
            refreshData()
        }
    }

    private fun refreshData() {
        mTestRefresh?.refresh()
    }

}