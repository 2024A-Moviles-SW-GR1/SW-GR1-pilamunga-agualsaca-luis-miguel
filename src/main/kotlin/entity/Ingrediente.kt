package com.Innova.entity

data class Ingrediente(
    override val id: Int,
    val nombre: String,
    val cantidad: Double,
    val unidadMedida: String
) : GenericSuperEntity()
