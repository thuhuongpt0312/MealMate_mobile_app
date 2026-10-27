package com.example.myapplication.data.model

data class Ingredient(
    val id: String,
    val name: String,
    val unit: String,
    val unitPrice: Double,
    val shelfLifeFridgeDays: Int,
    val shelfLifeFreezerDays: Int
)