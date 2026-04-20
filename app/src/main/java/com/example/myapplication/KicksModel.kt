package com.example.myapplication

import java.io.Serializable

data class KicksModel(
    val brand: String = "",
    val imageUrl: String= "",
    val modelName: String= "",
    val releaseYear: Int = 0,
    val resellPrice: Int = 0
) : Serializable
