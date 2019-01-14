package com.guangkuo.mvpfwk.utils

import android.support.v4.view.ViewPager

/**
 * viewPager 翻转切换工具类
 */
object TransformUtils {
    fun reverse(viewPager: ViewPager?, transformer: ViewPager.PageTransformer) {
        viewPager?.setPageTransformer(true, transformer)
    }

    fun forward(viewPager: ViewPager?, transformer: ViewPager.PageTransformer) {
        viewPager?.setPageTransformer(false, transformer)
    }
}