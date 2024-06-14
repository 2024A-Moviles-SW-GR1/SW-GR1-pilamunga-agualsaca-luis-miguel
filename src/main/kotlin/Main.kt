package com.Innova

import java.time.*
import java.time.format.DateTimeFormatter

fun main() {
    val pathIngrediente = "ingredientes.txt"
    val pathReceta = "recetas.txt"

    while (true) {
        println("\nSeleccione una opción:")
        println("1. Mostrar todas las recetas")
        println("2. Agregar una nueva receta")
        println("3. Eliminar una receta")
        println("4. Actualizar una receta")
        println("5. Mostrar todos los ingredientes")
        println("6. Agregar un nuevo ingrediente")
        println("7. Eliminar un ingrediente")
        println("8. Actualizar un ingrediente")
        println("9. Agregar un ingrediente a una receta")
        println("10. Salir")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                val recetas = Receta.cargarRecetas(pathReceta)
                if (recetas.isNotEmpty()) {
                    println("Recetas registradas:")
                    recetas.forEach { receta ->
                        println("ID: ${receta.id}, Nombre: ${receta.nombre}, Tiempo de Preparación: ${receta.tiempoPreparacion} minutos, Ingredientes: ${receta.ingredientes.map { it.nombre }}")
                    }
                } else {
                    println("No hay recetas registradas.")
                }
            }
            2 -> {
                println("Ingrese el nombre de la receta:")
                val nombre = readLine().toString()

                println("Ingrese el tiempo de preparación en minutos:")
                val tiempoPreparacion = readLine()?.toIntOrNull() ?: 0

                println("Ingrese el procedimiento de la receta:")
                val procedimiento = readLine().toString()

                Receta.crearYGuardarReceta(nombre, tiempoPreparacion, procedimiento, pathReceta)
                println("¡Receta agregada con éxito!")
            }
            3 -> {
                println("Ingrese el nombre de la receta a eliminar:")
                val nombre = readLine().toString()

                if (Receta.eliminarReceta(nombre, pathReceta)) {
                    println("¡Receta eliminada con éxito!")
                } else {
                    println("No se encontró una receta con el nombre especificado.")
                }
            }
            4 -> {
                println("Ingrese el nombre de la receta a actualizar:")
                val nombre = readLine().toString()

                println("Ingrese el nuevo tiempo de preparación en minutos:")
                val tiempoPreparacion = readLine()?.toIntOrNull() ?: 0

                println("Ingrese el nuevo procedimiento de la receta:")
                val procedimiento = readLine().toString()

                val recetas = Receta.cargarRecetas(pathReceta)
                val recetaActualizada = recetas.find { it.nombre == nombre }
                if (recetaActualizada != null) {
                    val actualizado = Receta(
                        recetaActualizada.id, nombre, tiempoPreparacion, procedimiento, recetaActualizada.ingredientes
                    )
                    if (Receta.actualizarReceta(actualizado, pathReceta)) {
                        println("¡Receta actualizada con éxito!")
                    }
                } else {
                    println("No se encontró una receta con el nombre especificado.")
                }
            }
            5 -> {
                val ingredientes = Ingrediente.cargarIngredientes(pathIngrediente)
                if (ingredientes.isNotEmpty()) {
                    println("Ingredientes registrados:")
                    ingredientes.forEach { ingrediente ->
                        println("ID: ${ingrediente.id}, Nombre: ${ingrediente.nombre}, Cantidad: ${ingrediente.cantidad}, Unidad: ${ingrediente.unidad}")
                    }
                } else {
                    println("No hay ingredientes registrados.")
                }
            }
            6 -> {
                println("Ingrese el nombre del ingrediente:")
                val nombre = readLine().toString()

                println("Ingrese la cantidad del ingrediente:")
                val cantidad = readLine()?.toDoubleOrNull() ?: 0.0

                println("Ingrese la unidad del ingrediente:")
                val unidad = readLine().toString()

                Ingrediente.crearYGuardarIngrediente(nombre, cantidad, unidad, pathIngrediente)
                println("¡Ingrediente agregado con éxito!")
            }
            7 -> {
                println("Ingrese el nombre del ingrediente a eliminar:")
                val nombre = readLine().toString()

                if (Ingrediente.eliminarIngrediente(nombre, pathIngrediente)) {
                    println("¡Ingrediente eliminado con éxito!")
                } else {
                    println("No se encontró un ingrediente con el nombre especificado.")
                }
            }
            8 -> {
                println("Ingrese el nombre del ingrediente a actualizar:")
                val nombre = readLine().toString()

                println("Ingrese la nueva cantidad del ingrediente:")
                val cantidad = readLine()?.toDoubleOrNull() ?: 0.0

                println("Ingrese la nueva unidad del ingrediente:")
                val unidad = readLine().toString()

                if (Ingrediente.actualizarIngrediente(nombre, cantidad, unidad, pathIngrediente)) {
                    println("¡Ingrediente actualizado con éxito!")
                } else {
                    println("No se encontró un ingrediente con el nombre especificado.")
                }
            }
            9 -> {
                println("Ingrese el ID de la receta a la que desea agregar un ingrediente:")
                val idReceta = readLine()?.toIntOrNull()
                println("Ingrese el ID del ingrediente que desea agregar:")
                val idIngrediente = readLine()?.toIntOrNull()

                if (idReceta != null && idIngrediente != null) {
                    val recetas = Receta.cargarRecetas(pathReceta).toMutableList()
                    val ingredientes = Ingrediente.cargarIngredientes(pathIngrediente).toMutableList()

                    val receta = recetas.find { it.id == idReceta }
                    val ingrediente = ingredientes.find { it.id == idIngrediente }

                    if (receta != null && ingrediente != null) {
                        receta.ingredientes.add(ingrediente)
                        Receta.actualizarReceta(receta, pathReceta)
                        println("¡Ingrediente agregado con éxito a la receta!")
                    } else {
                        println("No se encontró la receta o el ingrediente con el ID especificado.")
                    }
                } else {
                    println("IDs inválidos. Por favor, ingrese IDs válidos.")
                }
            }
            10 -> {
                println("Proceso terminado")
                return
            }
            else -> {
                println("Opción inválida. Por favor, seleccione una opción válida.")
            }
        }
    }
}