package com.example.myapplication.data.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val pantryItemsCount: Int,
    val savedMoney: Double,
    val hasExpiringItems: Boolean = false
)