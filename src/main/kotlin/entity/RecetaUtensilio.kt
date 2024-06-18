package com.Innova.entity

data class RecetaUtensilio(
    override var id: Int,
    val receta: Receta,
    val utensilio: Utensilio
): GenericSuperEntity()