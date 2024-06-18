package Controller

import com.Innova.entity.GenericSuperEntity
import com.Innova.service.GenericService

open class GenericCrudController<T : GenericSuperEntity>(private val service: GenericService<T>) {
    fun save(entity: T): String {
        return try {
            service.create(entity)
            "Registro Exitoso"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    fun findById(id: Int): T? {
        return service.read(id)
    }

    fun listAll(): List<T> {
        return service.getAll()
    }

    fun update(entity: T): String {
        return try {
            service.update(entity)
            "Actualización Exitosa"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    fun delete(id: Int): String {
        return try {
            service.delete(id)
            "Eliminación Exitosa"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}