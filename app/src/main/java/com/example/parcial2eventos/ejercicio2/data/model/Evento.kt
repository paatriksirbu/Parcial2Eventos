package com.example.parcial2eventos.ejercicio2.data.model

data class Evento(
    val id: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val direccion: String = "",
    val precio: Double = 0.0,
    val fecha: String = "",
    val fotoUrl: String? = null
)
