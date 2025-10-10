package com.ym.learn.data.model

class BaseResponse<T> {
    var count: Int = 0
    var next: String = ""
    var results: T? = null
}