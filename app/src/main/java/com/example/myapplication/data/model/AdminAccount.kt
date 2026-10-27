package com.example.myapplication.data.model

data class AdminAccount(
    val username: String = "admin",
    val role: String = "Quản trị viên",
    val active: Boolean = true
)