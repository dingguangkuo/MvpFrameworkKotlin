package com.guangkuo.mvpfwk.di.modules

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.guangkuo.mvpfwk.di.scopes.ContextLife
import com.guangkuo.mvpfwk.di.scopes.PerFragment
import dagger.Module
import dagger.Provides

/**
 *
 * Created by Guangkuo on 2018/12/29.
 */
@Module
class FragmentModule(private val mFragment: Fragment) {
    @Provides
    @PerFragment
    @ContextLife("Activity")
    fun provideActivityContext(): Context? {
        return mFragment.context
    }

    @Provides
    @PerFragment
    fun provideActivity(): Activity? {
        return mFragment.activity
    }

    @Provides
    @PerFragment
    fun provideFragment(): Fragment {
        return mFragment
    }
}