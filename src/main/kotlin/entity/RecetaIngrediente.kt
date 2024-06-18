package com.Innova.entity


data class RecetaIngrediente(
    override val id: Int,
    val receta: Receta,
    val ingrediente: Ingrediente,
    val cantidad: Double,
    val unidadMedida: String
) : GenericSuperEntity()