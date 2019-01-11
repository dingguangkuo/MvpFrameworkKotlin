package com.guangkuo.mvpfwk.di.components

import android.app.Activity
import android.content.Context
import com.guangkuo.mvpfwk.di.modules.ActivityModule
import com.guangkuo.mvpfwk.di.scopes.ContextLife
import com.guangkuo.mvpfwk.di.scopes.PerActivity
import com.guangkuo.mvpfwk.module.launcher.LauncherActivity
import com.guangkuo.mvpfwk.module.main.MainActivity
import com.guangkuo.mvpfwk.module.user.UserActivity
import dagger.Component

/**
 *
 * Created by Guangkuo on 2018/12/29.
 */
@PerActivity
@Component(dependencies = [AppComponent::class],
        modules = [ActivityModule::class])
interface ActivityComponent {
    @get:ContextLife("Activity")
    val activityContext: Context

    @get:ContextLife
    val applicationContext: Context

    val activity: Activity

    // 这个部分可以先不写，
    // 未来需要注入哪个activity写下就可以了
    fun inject(activity: LauncherActivity)

    fun inject(activity: UserActivity)

    fun inject(activity: MainActivity)
}