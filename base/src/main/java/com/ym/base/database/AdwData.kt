package com.ym.base.database

/**
 * 单例对象，提供统一的数据库和DataStore访问接口
 * 使用前必须先调用init方法初始化
 *
 */

object AdwData {
    private lateinit var database: AdwDatabase
    private lateinit var datastore: AdwDatastore

    fun init(dataFactory: DataFactory){
        database = dataFactory.cre
    }
}