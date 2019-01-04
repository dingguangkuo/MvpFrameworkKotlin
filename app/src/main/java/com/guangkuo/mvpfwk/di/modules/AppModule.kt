package com.guangkuo.mvpfwk.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.guangkuo.mvpfwk.app.App
import com.guangkuo.mvpfwk.di.scopes.ContextLife
import com.guangkuo.mvpfwk.di.scopes.PerApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *
 * Created by Guangkuo on 2018/12/29.
 */
@Module
class AppModule(private val mApp: App) {
    @Provides
    @PerApp
    @ContextLife
    fun provideApplicationContext(): Context {
        return mApp.applicationContext
    }

    @Provides
    @PerApp
    @ContextLife
    internal fun providesSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(mApp)
    }
}