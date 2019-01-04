package com.guangkuo.mvpfwk.di.components

import android.content.Context
import android.content.SharedPreferences
import com.guangkuo.mvpfwk.di.modules.AppModule
import com.guangkuo.mvpfwk.di.scopes.ContextLife
import com.guangkuo.mvpfwk.di.scopes.PerApp
import dagger.Component

/**
 *
 * Created by Guangkuo on 2018/12/29.
 */
@PerApp
@Component(modules = [AppModule::class])
interface AppComponent {
    @get:ContextLife
    val application: Context

    @get:ContextLife
    val sharedPreferences: SharedPreferences
}