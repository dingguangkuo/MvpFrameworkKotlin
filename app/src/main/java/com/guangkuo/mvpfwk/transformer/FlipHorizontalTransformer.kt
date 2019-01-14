package com.guangkuo.mvpfwk.transformer

import android.annotation.TargetApi
import android.os.Build
import android.view.View

class FlipHorizontalTransformer : TransformAdapter() {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onTransform(view: View, position: Float) {
        val rotation = 180f * position

        view.translationX = view.width * -position
        view.alpha = (if (rotation > 90f || rotation < -90f) 0 else 1).toFloat()
        view.pivotX = view.width * 0.5f
        view.pivotY = view.height * 0.5f
        view.rotationY = rotation

        if (position > -0.5f && position < 0.5f) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }
}