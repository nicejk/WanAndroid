package com.example.wanandroid.ui.activity

import android.content.Intent
import android.view.View
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMvpActivity
import com.example.wanandroid.constant.Constant
import com.example.wanandroid.event.LoginEvent
import com.example.wanandroid.ext.showToast
import com.example.wanandroid.mvp.contract.RegisterContract
import com.example.wanandroid.mvp.model.bean.LoginData
import com.example.wanandroid.mvp.presenter.RegisterPresenter
import com.example.wanandroid.utils.DialogUtil
import com.example.wanandroid.utils.Preference
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

class RegisterActivity : BaseMvpActivity<RegisterContract.View, RegisterContract.Presenter>(), RegisterContract.View, View.OnClickListener {
    // 用户名
    private var username: String by Preference(Constant.USERNAME_KEY, "")
    // 密码
    private var pwd: String by Preference(Constant.PASSWORD_KEY, "")

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.register_ing))
    }

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }

    override fun createPresenter(): RegisterContract.Presenter = RegisterPresenter()

    override fun attachLayoutRes(): Int = R.layout.activity_register

    override fun initView() {
        super.initView()
        toolbar.run {
            title = resources.getString(R.string.register)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        btn_register.setOnClickListener(this)
        tv_sign_in.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun start() {
    }

    override fun registerSuccess(data: LoginData) {
        showToast(getString(R.string.register_success))
        isLogin = true
        username = data.username
        pwd = data.password

        EventBus.getDefault().post(LoginEvent(true))
        finish()
    }

    override fun registerFail() {
        isLogin = false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                register()
            }
            R.id.tv_sign_in -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }
    }

    /**
     * 注册
     */
    private fun register() {
        if (validate()) {
            mPresenter?.registerWanAndroid(et_username.text.toString(), et_password.text.toString(), et_password2.text.toString())
        }
    }

    private fun validate(): Boolean {
        var valid = true
        val username: String = et_username.text.toString()
        val password: String = et_password.text.toString()
        val password2: String = et_password2.text.toString()

        if (username.isEmpty()) {
            et_username.error = getString(R.string.username_not_empty)
            valid = false
        }

        if (password.isEmpty()) {
            et_password.error = getString(R.string.password_not_empty)
            valid = false
        }
        if (password2.isEmpty()) {
            et_password2.error = getString(R.string.confirm_password_not_empty)
            valid = false
        }
        if (password != password2) {
            et_password2.error = getString(R.string.password_cannot_match)
            valid = false
        }
        return valid
    }
}