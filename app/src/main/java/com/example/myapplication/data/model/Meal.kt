package com.example.myapplication.data.model

data class Meal(
    val id: String,
    val name: String,
    val category: String,
    val price: Double,
    val prepTime: Int,
    val calories: Int,
    val ingredientsCount: Int,
    val imageUrl: String
)