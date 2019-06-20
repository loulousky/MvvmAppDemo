package com.example.liuhai.myapplication

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.DataBindingUtil.setContentView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.liuhai.myapplication.databinding.ActivityLoginBinding
import org.w3c.dom.Text
import kotlin.math.log


/**
 * 登陆的Activity
 */
class LoginActivity : AppCompatActivity() {
    val loginBinding by lazy { DataBindingUtil.setContentView<ActivityLoginBinding>(this,R.layout.activity_login) }
    private lateinit var loginViewModel: LoginViewModel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel=  ViewModelProviders.of(this,ViewModelFactory.getInstance()).get(LoginViewModel::class.java)
        loginBinding.viewmodel=loginViewModel
        loginViewModel.loginInfo.value=LoginInfo("我的账号","我的密码",0)
        loginViewModel.run {

            this.loginInfo.observe(this@LoginActivity,Observer<LoginInfo>{
                           //改变了
                Toast.makeText(application,"改变了"+it.toString(),0).show()
                //通知databinding 状态改变
                loginViewModel.notifyPropertyChanged(BR.loginInfo)
            })

        }

        //add的listen到销毁的时候最好都手动移除掉防止内存泄漏
      loginBinding.editAccount.addTextChangedListener(object :TextWatcher{
          override fun afterTextChanged(s: Editable?) {
              loginViewModel.loginInfo.value?.name=s.toString()
              //改变之后通过databinding生成的BR class进行通知
              loginViewModel.notifyChange()

           //   loginViewModel.loginInfo.postValue(loginViewModel.loginInfo.value);
              Toast.makeText(application,s.toString(),0).show()

          }

          override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
          }

          override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
          }
      })

        loginBinding.logoinbtn.setOnClickListener {

            loginViewModel.loginInfo.postValue(LoginInfo("帅比","1234567890",1))

        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
