package com.Innova.entity

data class Utensilio(
    override val id: Int,
    val nombre: String,
    val descripcion: String,
    val enlaceCompra: String
) : GenericSuperEntity()
