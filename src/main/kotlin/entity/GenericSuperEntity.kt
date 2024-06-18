package com.Innova.entity

import com.google.gson.Gson

abstract class GenericSuperEntity {
    abstract val id: Int

    open fun toJson(): String {
        return Gson().toJson(this)
    }
}

inline fun <reified T : GenericSuperEntity> String.fromJson(): T {
    return Gson().fromJson(this, T::class.java)
}
