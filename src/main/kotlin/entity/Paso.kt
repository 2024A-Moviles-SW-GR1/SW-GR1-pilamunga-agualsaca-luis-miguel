package com.Innova.entity

data class Paso(
    override val id: Int,
    val numeroPaso: Int,
    val descripcion: String,
    val receta: Receta
) : GenericSuperEntity()
