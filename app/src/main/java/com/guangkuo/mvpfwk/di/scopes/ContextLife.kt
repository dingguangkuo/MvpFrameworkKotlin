package com.guangkuo.mvpfwk.di.scopes

import javax.inject.Qualifier

/**
 * 用来标识
 */
@Qualifier
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ContextLife(val value: String = "Application")