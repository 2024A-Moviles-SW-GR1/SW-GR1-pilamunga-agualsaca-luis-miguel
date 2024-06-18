package com.Innova.entity
data class Categoria(
    override val id: Int,
    val nombre: String,
    val descripcion: String
): GenericSuperEntity()