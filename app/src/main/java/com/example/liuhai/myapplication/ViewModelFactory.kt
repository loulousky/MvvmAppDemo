package com.example.liuhai.myapplication

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * 作者：liuhai
 * 时间：2019/6/20:11:58
 * 邮箱：185587041@qq.com
 * 说明：
 */
class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        when (modelClass) {
            LoginViewModel::class.java ->
                return LoginViewModel() as T
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }

        return super.create(modelClass)
    }

    companion object {

        @Volatile
        private var viewModelFactory: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory {
            if (viewModelFactory == null) {
                synchronized(ViewModelFactory::class.java) {

                    if (viewModelFactory == null) {
                        viewModelFactory = ViewModelFactory()
                    }
                }
            }
            return viewModelFactory!!
        }
    }

}