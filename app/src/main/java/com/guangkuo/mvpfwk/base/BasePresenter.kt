package com.guangkuo.mvpfwk.base

/**
 * BasePresenter
 * @param <T> view
 */
open class BasePresenter<T : BaseContract.BaseView> : BaseContract.BasePresenter<T> {
    protected var mView: T? = null

    override fun attachView(view: T) {
        this.mView = view
    }

    override fun detachView() {
        mView = null
    }
}