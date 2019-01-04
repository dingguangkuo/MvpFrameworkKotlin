package com.guangkuo.mvpfwk.base

/**
 * 权限请求回调
 */
interface RequestPermissionCallback {
    fun onGranted(permissionsGranted: List<String>)

    fun onDenied(permissionsDeniedForever: List<String>, permissionsDenied: List<String>)
}