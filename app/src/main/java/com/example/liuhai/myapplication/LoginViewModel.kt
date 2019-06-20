package com.example.liuhai.myapplication

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.Bindable
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry



/**
 * 作者：liuhai
 * 时间：2019/6/20:10:24
 * 邮箱：185587041@qq.com
 * 说明：LiveData 和 databinding 结合 实现双向绑定
 */
class LoginViewModel : ViewModel(),Observable{
    private val callbacks = PropertyChangeRegistry()
    //保存用户的数据 对话框的数据监控
    @Bindable
    val loginInfo=MutableLiveData<LoginInfo>()
    //登陆结果的提示
    @Bindable
    val loginSuccessorFaile=MediatorLiveData<Boolean>()

    /***
     * 以下代码转子谷歌官方 livedata和databinding的结合实例
     *
     * https://developer.android.google.cn/topic/libraries/data-binding/architecture#kotlin
     */
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }
    /**
     * Notifies observers that all properties of this instance have changed.
     */
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}