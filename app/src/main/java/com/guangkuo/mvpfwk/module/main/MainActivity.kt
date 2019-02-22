package com.guangkuo.mvpfwk.module.main

import android.view.View
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener
import com.guangkuo.mvpfwk.R
import com.guangkuo.mvpfwk.base.BaseActivity
import com.guangkuo.mvpfwk.utils.JniUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainContract.View, MainPresenter>(), MainContract.View, View.OnClickListener {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun useCustomTitle(): Boolean {
        return true
    }

    override fun initInjector() {
        mActivityComponent.inject(this)
    }

    override fun initView() {
        sample_text.text = JniUtils().loadCString()
    }

    override fun bindListener() {
        btnSelectPic.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btnSelectPic -> {
                RxGalleryFinalApi
                        .onCrop(true)
                        .openGalleryRadioImgDefault(object : RxBusResultDisposable<ImageRadioResultEvent>() {
                            override fun onEvent(resultEvent: ImageRadioResultEvent) {
                            }
                        })
                        .onCropImageResult(object : IRadioImageCheckedListener {
                            override fun cropAfter(t: Any) {
                            }

                            override fun isActivityFinish(): Boolean {
                                return false
                            }
                        })
            }
            else -> {

            }
        }
    }
}
