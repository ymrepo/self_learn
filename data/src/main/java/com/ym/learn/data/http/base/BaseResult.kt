package com.ym.learn.data.http.base

open class BaseResult<T> {
    val code: String? = null
    val message: String? = null
    val data: T? = null
}