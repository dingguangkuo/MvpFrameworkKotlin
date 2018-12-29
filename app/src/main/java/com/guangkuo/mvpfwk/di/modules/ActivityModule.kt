package com.guangkuo.mvpfwk.di.modules

import android.app.Activity
import android.content.Context
import com.guangkuo.mvpfwk.di.scopes.ContextLife
import com.guangkuo.mvpfwk.di.scopes.PerActivity
import dagger.Module
import dagger.Provides

/**
 *
 * Created by Guangkuo on 2018/12/29.
 */
@Module
class ActivityModule(private val mActivity: Activity) {
    @PerActivity
    @Provides
    @ContextLife("Activity")
    fun provideActivityContext(): Context {
        return mActivity
    }

    @Provides
    @PerActivity
    fun provideActivity(): Activity {
        return mActivity
    }
}