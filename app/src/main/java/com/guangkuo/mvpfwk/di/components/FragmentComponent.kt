package com.guangkuo.mvpfwk.di.components

import android.app.Activity
import android.content.Context
import com.guangkuo.mvpfwk.di.modules.FragmentModule
import com.guangkuo.mvpfwk.di.scopes.ContextLife
import com.guangkuo.mvpfwk.di.scopes.PerFragment
import com.guangkuo.mvpfwk.module.user.login.LoginFragment
import com.guangkuo.mvpfwk.module.user.register.RegisterFragment
import dagger.Component

/**
 *
 * Created by Guangkuo on 2018/12/29.
 */
@PerFragment
@Component(dependencies = [AppComponent::class],
        modules = [FragmentModule::class])
interface FragmentComponent {
    @get:ContextLife("Activity")
    val activityContext: Context

    @get:ContextLife("Application")
    val applicationContext: Context

    val activity: Activity

    // 这个部分可以先不写，
    // 未来需要注入哪个fragment写下就可以了
    fun inject(fragment: LoginFragment)

    fun inject(fragment: RegisterFragment)
}