package com.Innova

import java.io.File
import java.nio.charset.Charset

class Receta(
    val id: Int,
    val nombre: String,
    val tiempoPreparacion: Int,
    val procedimiento: String,
    val ingredientes: MutableList<Ingrediente> = mutableListOf()
) {
    companion object {
        private var nextId: Int = 1

        fun guardarReceta(receta: Receta, filePath: String) {
            val datosReceta = "${receta.id},${receta.nombre},${receta.tiempoPreparacion},${receta.procedimiento},${receta.ingredientes.toString()}\n"
            File(filePath).appendText(datosReceta, Charset.defaultCharset())
        }

        fun cargarRecetas(filePath: String): List<Receta> {
            val recetasData = File(filePath).readLines()
            val recetas = mutableListOf<Receta>()
            for (recetaData in recetasData) {
                val parts = recetaData.split(",")
                if (parts.size >= 4) {
                    val id = parts[0].toInt()
                    val nombre = parts[1]
                    val tiempoPreparacion = parts[2].toInt()
                    val procedimiento = parts[3]
                    recetas.add(Receta(id, nombre, tiempoPreparacion, procedimiento))
                    if (id >= nextId) {
                        nextId = id + 1
                    }
                } else {
                    println("Error: Formato de datos incorrecto en la línea: $recetaData")
                }
            }
            return recetas
        }

        fun eliminarReceta(nombre: String, filePath: String): Boolean {
            val recetas = cargarRecetas(filePath).toMutableList()
            val iterator = recetas.iterator()
            var eliminado = false
            while (iterator.hasNext()) {
                val receta = iterator.next()
                if (receta.nombre == nombre) {
                    iterator.remove()
                    eliminado = true
                }
            }
            if (eliminado) {
                File(filePath).writeText("") // Borra el archivo actual
                recetas.forEach { guardarReceta(it, filePath) }
            }
            return eliminado
        }

        fun actualizarReceta(recetaActualizada: Receta, filePath: String): Boolean {
            val recetas = cargarRecetas(filePath).toMutableList()
            var actualizado = false
            for (i in recetas.indices) {
                if (recetas[i].id == recetaActualizada.id) {
                    recetas[i] = Receta(
                        recetas[i].id,
                        recetaActualizada.nombre,
                        recetaActualizada.tiempoPreparacion,
                        recetaActualizada.procedimiento,
                        recetaActualizada.ingredientes
                    )
                    actualizado = true
                    break
                }
            }
            if (actualizado) {
                File(filePath).writeText("") // Borra el archivo actual
                recetas.forEach { guardarReceta(it, filePath) }
            }
            return actualizado
        }

        fun crearYGuardarReceta(nombre: String, tiempoPreparacion: Int, procedimiento: String, filePath: String) {
            val receta = Receta(nextId++, nombre, tiempoPreparacion, procedimiento)
            guardarReceta(receta, filePath)
        }
    }
}