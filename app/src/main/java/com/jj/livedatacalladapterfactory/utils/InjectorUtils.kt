package com.jj.livedatacalladapterfactory.utils

import com.jj.livedatacalladapterfactory.data.MainRepository
import com.jj.livedatacalladapterfactory.viewmodels.MainViewModelFactory

object InjectorUtils {
    fun provideMainViewModelFactory(): MainViewModelFactory {
        val repository = getMainRepository()
        return MainViewModelFactory(repository)
    }

    private fun getMainRepository(): MainRepository {
        return MainRepository.getInstance()
    }
}