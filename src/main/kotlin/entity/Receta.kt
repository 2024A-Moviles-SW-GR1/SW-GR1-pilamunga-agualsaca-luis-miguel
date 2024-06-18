package com.Innova.entity


data class Receta(
    override val id: Int,
    val nombre: String,
    val descripcion: String,
    val tiempoPreparacion: Int,
    val porciones: Int,
    val dificultad: String,
    val categoria: Categoria,
    val fechaActualizacion: String,
    val ingredientes: List<RecetaIngrediente>,
    val pasos: List<Paso>,
    val utensilios: List<Utensilio>
): GenericSuperEntity()