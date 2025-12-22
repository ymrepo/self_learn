package com.ym.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

// 使用扩展属性创建 DataStore 实例
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

class DataStoreUtil(private val context: Context) {

    /**
     * 存储字符串
     */
    suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    /**
     * 获取字符串
     */
    fun getString(key: String, default: String = ""): Flow<String> {
        val preferencesKey = stringPreferencesKey(key)
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[preferencesKey] ?: default
            }
    }

    /**
     * 存储整数
     */
    suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    /**
     * 获取整数
     */
    fun getInt(key: String, default: Int = 0): Flow<Int> {
        val preferencesKey = intPreferencesKey(key)
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[preferencesKey] ?: default
            }
    }

    /**
     * 存储长整型
     */
    suspend fun putLong(key: String, value: Long) {
        val preferencesKey = longPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    /**
     * 获取长整型
     */
    fun getLong(key: String, default: Long = 0L): Flow<Long> {
        val preferencesKey = longPreferencesKey(key)
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[preferencesKey] ?: default
            }
    }

    /**
     * 存储布尔值
     */
    suspend fun putBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    /**
     * 获取布尔值
     */
    fun getBoolean(key: String, default: Boolean = false): Flow<Boolean> {
        val preferencesKey = booleanPreferencesKey(key)
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[preferencesKey] ?: default
            }
    }

    /**
     * 存储浮点数
     */
    suspend fun putFloat(key: String, value: Float) {
        val preferencesKey = floatPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    /**
     * 获取浮点数
     */
    fun getFloat(key: String, default: Float = 0f): Flow<Float> {
        val preferencesKey = floatPreferencesKey(key)
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[preferencesKey] ?: default
            }
    }

    /**
     * 存储字符串集合
     */
    suspend fun putStringSet(key: String, value: Set<String>) {
        val preferencesKey = stringSetPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    /**
     * 获取字符串集合
     */
    fun getStringSet(key: String, default: Set<String> = emptySet()): Flow<Set<String>> {
        val preferencesKey = stringSetPreferencesKey(key)
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[preferencesKey] ?: default
            }
    }

    /**
     * 删除指定键
     */
    suspend fun remove(key: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences.remove(preferencesKey)
        }
    }

    /**
     * 清空所有数据
     */
    suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    /**
     * 检查是否包含某个键
     */
    fun contains(key: String): Flow<Boolean> {
        val preferencesKey = stringPreferencesKey(key)
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences.contains(preferencesKey)
            }
    }

    /**
     * 同步获取值（在协程中使用）
     */
    suspend fun getStringSync(key: String, default: String = ""): String {
        return getString(key, default).first()
    }

    suspend fun getIntSync(key: String, default: Int = 0): Int {
        return getInt(key, default).first()
    }

    suspend fun getBooleanSync(key: String, default: Boolean = false): Boolean {
        return getBoolean(key, default).first()
    }

    suspend fun getLongSync(key: String, default: Long = 0L): Long {
        return getLong(key, default).first()
    }

    suspend fun getFloatSync(key: String, default: Float = 0f): Float {
        return getFloat(key, default).first()
    }
}