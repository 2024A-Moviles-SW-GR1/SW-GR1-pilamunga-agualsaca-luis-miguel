package com.Innova

import java.io.File

class Ingrediente(
    val id: Int,
    val nombre: String,
    val cantidad: Double,
    val unidad: String
) {
    companion object {
        private var nextId: Int = 1

        fun guardarIngrediente(ingrediente: Ingrediente, filePath: String) {
            val datosIngrediente = "${ingrediente.id},${ingrediente.nombre},${ingrediente.cantidad},${ingrediente.unidad}\n"
            File(filePath).appendText(datosIngrediente, Charsets.UTF_8)
        }

        fun cargarIngredientes(filePath: String): List<Ingrediente> {
            val ingredientesData = File(filePath).readLines()
            val ingredientes = mutableListOf<Ingrediente>()
            for (ingredienteData in ingredientesData) {
                val parts = ingredienteData.split(",")
                if (parts.size >= 4) {
                    val id = parts[0].toInt()
                    val nombre = parts[1]
                    val cantidad = parts[2].toDouble()
                    val unidad = parts[3]
                    ingredientes.add(Ingrediente(id, nombre, cantidad, unidad))
                    if (id >= nextId) {
                        nextId = id + 1
                    }
                } else {
                    println("Error: Formato de datos incorrecto en la línea: $ingredienteData")
                }
            }
            return ingredientes
        }

        fun eliminarIngrediente(nombre: String, filePath: String): Boolean {
            val ingredientes = cargarIngredientes(filePath).toMutableList()
            val iterator = ingredientes.iterator()
            var eliminado = false
            while (iterator.hasNext()) {
                val ingrediente = iterator.next()
                if (ingrediente.nombre == nombre) {
                    iterator.remove()
                    eliminado = true
                }
            }
            if (eliminado) {
                File(filePath).writeText("") // Borra el archivo actual
                ingredientes.forEach { guardarIngrediente(it, filePath) }
            }
            return eliminado
        }

        fun actualizarIngrediente(nombre: String, cantidad: Double, unidad: String, filePath: String): Boolean {
            val ingredientes = cargarIngredientes(filePath).toMutableList()
            var actualizado = false
            for (i in ingredientes.indices) {
                val ingrediente = ingredientes[i]
                if (ingrediente.nombre == nombre) {
                    ingredientes[i] = Ingrediente(ingrediente.id, nombre, cantidad, unidad)
                    actualizado = true
                    break
                }
            }
            if (actualizado) {
                File(filePath).writeText("") // Borra el archivo actual
                ingredientes.forEach { guardarIngrediente(it, filePath) }
            }
            return actualizado
        }

        fun crearYGuardarIngrediente(nombre: String, cantidad: Double, unidad: String, filePath: String) {
            val ingrediente = Ingrediente(nextId++, nombre, cantidad, unidad)
            guardarIngrediente(ingrediente, filePath)
        }
    }
}