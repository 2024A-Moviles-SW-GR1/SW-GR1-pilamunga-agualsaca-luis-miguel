package com.Innova.service

import com.Innova.entity.GenericSuperEntity
import com.google.gson.Gson
import java.io.File

open class GenericService<T : GenericSuperEntity>(private val clazz: Class<T>) {
    private val fileName: String = "src/main/kotlin/files/${clazz.simpleName}.txt"
    private val gson = Gson()

    fun create(entity: T) {
        val file = File(fileName)
        file.appendText("${entity.toJson()}\n")
    }

    fun read(id: Int): T? {
        val file = File(fileName)
        if (!file.exists()) return null

        file.useLines { lines ->
            lines.forEach { line ->
                val entity = gson.fromJson(line, clazz)
                if (entity.id == id) {
                    return entity
                }
            }
        }
        return null
    }

    fun update(entity: T) {
        val file = File(fileName)
        if (!file.exists()) return

        val updatedContent = file.readLines().map { line ->
            val existingEntity = gson.fromJson(line, clazz)
            if (existingEntity.id == entity.id) {
                entity.toJson()
            } else {
                line
            }
        }.joinToString("\n")

        file.writeText(updatedContent)
    }

    fun delete(id: Int) {
        val file = File(fileName)
        if (!file.exists()) return

        val remainingContent = file.readLines().filter { line ->
            val entity = gson.fromJson(line, clazz)
            entity.id != id
        }.joinToString("\n")

        file.writeText(remainingContent)
    }

    fun getAll(): List<T> {
        val file = File(fileName)
        if (!file.exists()) return emptyList()

        return file.readLines().map { line ->
            gson.fromJson(line, clazz)
        }
    }
}
