package com.guangkuo.mvpfwk.widget

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * 自定义ViewPager（可禁止滑动）
 * Created by Guangkuo on 2019/1/14.
 */
class CustomViewPager : ViewPager {
    private var isCanScroll = true

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    /**
     * 禁用滑动换页
     */
    fun disableScroll() {
        this.isCanScroll = false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return isCanScroll && super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return isCanScroll && super.onTouchEvent(ev)
    }
}