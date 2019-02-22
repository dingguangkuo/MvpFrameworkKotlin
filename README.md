# MvpFrameworkKotlin
使用 Kotlin + MVP + RxJava2 + Retrofit2 + Dagger2 搭建 Android 应用快速开发框架

## 基本数据类型





## Kotlin中 ?  ?:  ?.  !!

```kotlin
?可空类型，可以赋值为null
?.就是当前面的变量!= null 时正常调用，如果为null就为null；
	print(user?.userName?.length)// user==null 或者 userName==null都会输出null
!!就是当变量为null时，抛出空指针异常；
?:操作符，elvis操作符，这个其实和可空类型没啥关系，这个也不是Java中的三目运算符
	var nameLen: Int = name?.length ?: 0 // 仅在左边的表达式结果为空时才会计算?:后面的表达式
```

