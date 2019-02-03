package com.guangkuo.mvpfwk.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList

/**
 * 通用FragmentPagerAdapter
 * Created by Guangkuo on 2019/1/14.
 */
class CommonFragmentPagerAdapter : FragmentPagerAdapter {
    private var mTitles: MutableList<String>? = null
    private var mFragments: MutableList<Fragment>? = null

    constructor(fm: FragmentManager) : super(fm)

    constructor(fm: FragmentManager, titles: MutableList<String>) : super(fm) {
        mTitles = titles
    }

    override fun getItem(position: Int): Fragment? {
        return mFragments?.get(position)
    }

    override fun getCount(): Int {
        return mFragments?.size ?: 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles?.get(position)
    }

    fun addFragment(fragment: Fragment) {
        if (mFragments == null) {
            mFragments = ArrayList()
        }
        mFragments?.add(fragment)
    }

    fun addFragment(title: String, fragment: Fragment) {
        if (mTitles == null) {
            mTitles = ArrayList()
        }
        mTitles?.add(title)
        if (mFragments == null) {
            mFragments = ArrayList()
        }
        mFragments?.add(fragment)
    }
}