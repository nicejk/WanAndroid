package com.example.wanandroid.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentTransaction
import com.example.wanandroid.R
import com.example.wanandroid.app.App
import com.example.wanandroid.base.BaseMvpActivity
import com.example.wanandroid.constant.Constant
import com.example.wanandroid.event.ColorEvent
import com.example.wanandroid.event.LoginEvent
import com.example.wanandroid.ext.showToast
import com.example.wanandroid.mvp.contract.MainContract
import com.example.wanandroid.mvp.model.bean.UserInfoBody
import com.example.wanandroid.mvp.presenter.MainPresenter
import com.example.wanandroid.ui.fragment.*
import com.example.wanandroid.utils.DialogUtil
import com.example.wanandroid.utils.Preference
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.navigation.NavigationView
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(),
    MainContract.View {
    private val BOTTOM_INDEX = "bottom_index"

    private val FRAGMENT_HOME = 0X01
    private val FRAGMENT_SQUARE = 0x02
    private val FRAGMENT_WECHAT = 0X03
    private val FRAGMENT_SYSTEM = 0x04
    private val FRAGMENT_PROJECT = 0x05

    private var mIndex = FRAGMENT_HOME

    private var mHomeFragment: HomeFragment? = null
    private var mSquareFragment: SquareFragment? = null
    private var mWeChatFragment: WeChatFragment? = null
    private var mSystemFragment: SystemFragment? = null
    private var mProjectFragment: ProjectFragment? = null

    // 本地用户名
    private var mUserName: String by Preference(Constant.USERNAME_KEY, "")

    // 用户名
    private var mNavUserName: TextView? = null

    // 用户id
    private var mNavUserId: TextView? = null

    // 用户等级
    private var mNavUserGrade: TextView? = null

    // 我的排名
    private var mNavUserRank: TextView? = null

    // 积分
    private var mNavScore: TextView? = null

    // 排行榜
    private var mNavRank: ImageView? = null

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun attachLayoutRes(): Int = R.layout.activity_main

    override fun useEventBus(): Boolean = true

    override fun initData() {
        Beta.checkUpgrade(false, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt(BOTTOM_INDEX)
        }
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        super.initView()
        toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }

        bottom_navigation.run {
            labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }

        initDrawerLayout()

        initNavView()

        showFragment(mIndex)

        mFloatingActionBtn.run {
            setOnClickListener(onFABClickListener)
        }
    }

    private fun initNavView() {
        mNavigationView.run {
            setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
            mNavUserName = getHeaderView(0).findViewById(R.id.tv_username)
            mNavUserId = getHeaderView(0).findViewById(R.id.tv_user_id)
            mNavUserGrade = getHeaderView(0).findViewById(R.id.tv_user_grade)
            mNavUserRank = getHeaderView(0).findViewById(R.id.tv_user_rank)
            mNavRank = getHeaderView(0).findViewById(R.id.iv_rank)
            mNavScore = getHeaderView(0).findViewById(R.id.nav_score)
            mNavScore?.gravity = Gravity.CENTER_VERTICAL
            menu.findItem(R.id.nav_logout).isVisible = isLogin
        }
        mNavUserName?.run {
            text = if (!isLogin) getString(R.string.go_login) else mUserName
            setOnClickListener {
                if (!isLogin) {
                    goLogin()
                }
            }
        }
        mNavRank?.setOnClickListener {
            startActivity(Intent(this@MainActivity, RankActivity::class.java))
        }
    }

    private fun initDrawerLayout() {
        drawer_layout.run {
            val toggle = ActionBarDrawerToggle(
                this@MainActivity,
                this,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    override fun start() {
        mPresenter?.getUserInfo()
    }

    override fun initColor() {
        super.initColor()
        refreshColor(ColorEvent(true))
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            return@OnNavigationItemSelectedListener when (it.itemId) {
                R.id.action_home -> {
                    showFragment(FRAGMENT_HOME)
                    true
                }
                R.id.action_square -> {
                    showFragment(FRAGMENT_SQUARE)
                    true
                }
                R.id.action_wechat -> {
                    showFragment(FRAGMENT_WECHAT)
                    true
                }
                R.id.action_system -> {
                    showFragment(FRAGMENT_SYSTEM)
                    true
                }
                R.id.action_project -> {
                    showFragment(FRAGMENT_PROJECT)
                    true
                }
                else -> {
                    false
                }
            }
        }

    private val onDrawerNavigationItemSelectedListener =
        NavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_score -> {
                    if (isLogin) {
                        // TODO: 2020/11/27 跳转到积分页
                    } else {
                        showToast(resources.getString(R.string.login_tint))
                        goLogin()
                    }
                }
                R.id.nav_collect -> {

                }

                R.id.nav_share -> {

                }

                R.id.nav_todo -> {

                }

                R.id.nav_night_mode -> {

                }

                R.id.nav_setting -> {

                }

                R.id.nav_logout -> {
                    logout()
                }
            }
            true
        }

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, resources.getString(R.string.logout_ing))
    }

    /**
     * 退出登录
     */
    private fun logout() {
        DialogUtil.getConfirmDialog(
            this,
            resources.getString(R.string.confirm_logout),
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    mDialog.show()
                    mPresenter?.logout()
                }
            }).show()
    }

    private val onFABClickListener = View.OnClickListener {
        when (mIndex) {
            // 首页
            FRAGMENT_HOME -> {
                mHomeFragment?.scrollToTop()
            }
            // 广场
            FRAGMENT_SQUARE -> {

            }
            // 公众号
            FRAGMENT_WECHAT -> {

            }
            // 体系
            FRAGMENT_SYSTEM -> {

            }
            // 项目
            FRAGMENT_PROJECT -> {

            }
        }
    }

    /**
     * 去登录页面
     */
    private fun goLogin() {
        Intent(this@MainActivity, LoginActivity::class.java).run {
            startActivity(this)
        }
    }

    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        mIndex = index
        when (index) {
            FRAGMENT_HOME -> {
                toolbar.title = getString(R.string.app_name)
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.getInstance()
                    transaction.add(R.id.container, mHomeFragment!!, "home")
                } else {
                    transaction.show(mHomeFragment!!)
                }
            }
            FRAGMENT_SQUARE -> {
                toolbar.title = getString(R.string.square)
                if (mSquareFragment == null) {
                    mSquareFragment = SquareFragment.getInstance()
                    transaction.add(R.id.container, mSquareFragment!!, "square")
                } else {
                    transaction.show(mSquareFragment!!)
                }
            }
            FRAGMENT_WECHAT -> {
                toolbar.title = getString(R.string.wechat)
                if (mWeChatFragment == null) {
                    mWeChatFragment = WeChatFragment.getInstance()
                    transaction.add(R.id.container, mWeChatFragment!!, "wechat")
                } else {
                    transaction.show(mWeChatFragment!!)
                }
            }
            FRAGMENT_SYSTEM -> {
                toolbar.title = getString(R.string.knowledge_system)
                if (mSystemFragment == null) {
                    mSystemFragment = SystemFragment.getInstance()
                    transaction.add(R.id.container, mSystemFragment!!, "system")
                } else {
                    transaction.show(mSystemFragment!!)
                }
            }
            FRAGMENT_PROJECT -> {
                toolbar.title = getString(R.string.project)
                if (mProjectFragment == null) {
                    mProjectFragment = ProjectFragment.getInstance()
                    transaction.add(R.id.container, mProjectFragment!!, "project")
                } else {
                    transaction.show(mProjectFragment!!)
                }
            }
        }
        transaction.commit()
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mSquareFragment?.let { transaction.hide(it) }
        mWeChatFragment?.let { transaction.hide(it) }
        mSystemFragment?.let { transaction.hide(it) }
        mProjectFragment?.let { transaction.hide(it) }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshColor(event: ColorEvent) {
        if (event.isRefresh) {
            mNavigationView.getHeaderView(0).setBackgroundColor(mThemeColor)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                mFloatingActionBtn.backgroundTintList = ColorStateList.valueOf(mThemeColor)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginEvent(event: LoginEvent) {
        if (event.isLogin) {
            mNavUserName?.text = mUserName
            mNavigationView?.menu?.findItem(R.id.nav_logout)?.isVisible = true
            mHomeFragment?.lazyLoad()
            mPresenter?.getUserInfo()
        } else {
            mNavUserName?.text = resources.getString(R.string.go_login)
            mNavigationView?.menu?.findItem(R.id.nav_logout)?.isVisible = false
            mHomeFragment?.lazyLoad()
            mNavUserId?.text = getString(R.string.nav_line_4)
            mNavUserGrade?.text = getString(R.string.nav_line_2)
            mNavUserRank?.text = getString(R.string.nav_line_2)
            mNavScore?.text = getString(R.string.nav_line_2)
        }
    }

    override fun showLogoutSuccess(success: Boolean) {
        if (success) {
            doAsync {
                Preference.clearPreference()
                uiThread {
                    mDialog.dismiss()
                    showToast(resources.getString(R.string.logout_success))
                    mUserName = mNavUserName?.text.toString().trim()
                    isLogin = false
                    EventBus.getDefault().post(LoginEvent(false))
                }
            }
        }
    }

    override fun showUserInfo(bean: UserInfoBody) {
        App.userInfo = bean

        mNavUserId?.text = bean.userId.toString()
        mNavUserGrade?.text = (bean.coinCount / 100 + 1).toString()
        mNavUserRank?.text = bean.rank.toString()
        mNavScore?.text = bean.coinCount.toString()
    }
}