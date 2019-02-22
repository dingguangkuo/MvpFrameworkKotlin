package com.guangkuo.mvpfwk.app

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.guangkuo.mvpfwk.di.components.AppComponent
import com.guangkuo.mvpfwk.di.components.DaggerAppComponent
import com.guangkuo.mvpfwk.di.modules.AppModule

/**
 * Application
 * Created by Guangkuo on 2018/12/29.
 */
class App : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        initComponent()// 初始化组建
    }

    private fun initComponent() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        fun getAppComponent(app: App): AppComponent {
            return app.appComponent
        }
    }
}