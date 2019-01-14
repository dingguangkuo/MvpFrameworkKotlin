package com.guangkuo.mvpfwk.transformer

import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View

abstract class TransformAdapter : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {

        if (position > 0 && position <= 1) {
            onRightScorlling(page, position)
        } else if (position < 0 && position >= -1) {
            onLeftScorlling(page, position)
        } else if (position == 0f) {
            onCenterIdle(page)
        }
        onTransform(page, position)
    }

    /**
     * @param view     right view
     * @param position right to center 1->0
     * center to right 0->1
     */
    fun onRightScorlling(view: View, position: Float) {
    }

    /**
     * @param view     left view
     * @param position left to center  -1->0
     * center to left  0->-1
     */
    fun onLeftScorlling(view: View, position: Float) {
    }

    fun onCenterIdle(view: View) {
    }

    /**
     *
     * @param view left and right view both callback
     * @param position [-1,1]
     */
    open fun onTransform(view: View, position: Float) {
    }

    fun log(clazz: Class<out TransformAdapter>, msg: String) {
        Log.d(clazz.simpleName, msg)
    }
}