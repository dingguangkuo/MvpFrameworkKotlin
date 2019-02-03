package com.guangkuo.mvpfwk.module.main

import com.guangkuo.mvpfwk.base.BasePresenter
import javax.inject.Inject

/*
 当遇到@Inject注解的时候处理步骤：
 1. 查找Module中是否有创建该类的方法
 2. 若存在创建该类的方法，查看该方法是否有参数
    2.1 有参数，实例化该类，并且从  步骤1  开始初始化每个参数
    2.2  没有参数，直接实例化该类，一次注入完成
 3. 不存在创建该类的方法，查找Inject注解的构造函数，看构造函数是否存在参数
    3.1：若存在参数，则  步骤1   开始依次初始化每个参数
    3.2：若不存在参数，则直接初始化该类实例，一次依赖注入完成.
 */
class MainPresenter @Inject constructor()
    : BasePresenter<MainContract.View>(), MainContract.Presenter {
    //
}
