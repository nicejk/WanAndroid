package com.example.wanandroid.ui.activity

import android.content.Intent
import android.view.View
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMvpActivity
import com.example.wanandroid.constant.Constant
import com.example.wanandroid.event.LoginEvent
import com.example.wanandroid.ext.showToast
import com.example.wanandroid.mvp.contract.LoginContract
import com.example.wanandroid.mvp.model.bean.LoginData
import com.example.wanandroid.mvp.presenter.LoginPresetner
import com.example.wanandroid.utils.DialogUtil
import com.example.wanandroid.utils.Preference
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseMvpActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View, View.OnClickListener {
    // 用户名
    private var username: String by Preference(Constant.USERNAME_KEY, "")
    // 密码
    private var pwd: String by Preference(Constant.PASSWORD_KEY, "")
    // token
    private var token: String by Preference(Constant.TOKEN_KEY, "")

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.login_ing))
    }

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }

    override fun createPresenter(): LoginContract.Presenter = LoginPresetner()

    override fun attachLayoutRes(): Int = R.layout.activity_login

    override fun initView() {
        super.initView()
        mEtUserName.setText(username)
        toolbar.run {
            title = resources.getString(R.string.login)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        mTvLogin.setOnClickListener(this)
        mTvRegister.setOnClickListener(this)
    }

    override fun initData() {

    }

    override fun start() {

    }



    override fun loginSuccess(data: LoginData) {
        showToast(getString(R.string.login_success))
        isLogin = true
        username = data.username
        pwd = data.password
        token = data.token

        EventBus.getDefault().post(LoginEvent(true))
        finish()
    }

    override fun loginFail() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTvLogin -> {
                login()
            }
            R.id.mTvRegister -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    /**
     * 登录
     */
    private fun login() {
        if (validate()) {
            mPresenter?.loginWanAndroid(mEtUserName.text.toString(), mEtPwd.text.toString())
        }
    }

    /**
     * 验证登录信息（用户名，密码）
     */
    private fun validate(): Boolean {
        var valid = true
        val username: String = mEtUserName.text.toString()
        val password: String = mEtPwd.text.toString()

        if (username.isEmpty()) {
            mEtUserName.error = getString(R.string.username_not_empty)
            valid = false
        }

        if (password.isEmpty()) {
            mEtPwd.error = getString(R.string.password_not_empty)
            valid = false
        }
        return valid
    }
}