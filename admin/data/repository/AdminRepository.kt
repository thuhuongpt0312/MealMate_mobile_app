package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Ingredient
import com.example.myapplication.data.model.Meal
import com.example.myapplication.data.model.User

object AdminRepository {

    val sampleMeals = listOf(
        Meal(
            id = "1",
            name = "Cơm gà xào gừng sốt tương",
            category = "Nhanh gọn",
            price = 45000.0,
            prepTime = 20,
            calories = 520,
            ingredientsCount = 5,
            imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=600&q=80"
        ),
        Meal(
            id = "2",
            name = "Phở bò tái gừng tươi",
            category = "Truyền thống",
            price = 55000.0,
            prepTime = 30,
            calories = 580,
            ingredientsCount = 6,
            imageUrl = "https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?auto=format&fit=crop&w=600&q=80"
        ),
        Meal(
            id = "3",
            name = "Bánh mì nướng bơ trứng",
            category = "Lành mạnh",
            price = 30000.0,
            prepTime = 10,
            calories = 360,
            ingredientsCount = 4,
            imageUrl = "https://images.unsplash.com/photo-1525351484163-7529414344d8?auto=format&fit=crop&w=600&q=80"
        ),
        Meal(
            id = "4",
            name = "Cơm cá hồi áp chảo rau củ",
            category = "Gia đình",
            price = 85000.0,
            prepTime = 25,
            calories = 610,
            ingredientsCount = 6,
            imageUrl = "https://images.unsplash.com/photo-1467003909585-2f8a72700288?auto=format&fit=crop&w=600&q=80"
        ),
        Meal(
            id = "5",
            name = "Đậu phụ sốt cà chua",
            category = "Tiết kiệm",
            price = 25000.0,
            prepTime = 15,
            calories = 420,
            ingredientsCount = 4,
            imageUrl = "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=600&q=80"
        )
    )

    val sampleIngredients = listOf(
        Ingredient("ing-1", "Ức gà tươi phi lê", "g", 90.0, 3, 90),
        Ingredient("ing-2", "Trứng gà ta", "quả", 3500.0, 28, 0),
        Ingredient("ing-3", "Gạo lài thơm", "kg", 25000.0, 180, 360),
        Ingredient("ing-4", "Rau bina (cải bó xôi)", "g", 70.0, 4, 30),
        Ingredient("ing-5", "Cà rốt Đà Lạt", "g", 30.0, 21, 60),
        Ingredient("ing-6", "Nước tương cao cấp", "ml", 60.0, 180, 0),
        Ingredient("ing-7", "Cá hồi phi lê", "g", 450.0, 2, 60),
        Ingredient("ing-8", "Đậu phụ non", "miếng", 5000.0, 4, 14)
    )

    val sampleUsers = listOf(
        User("u1", "Maya Nguyen", "maya.nguyen@gmail.com", 8, 235000.0, hasExpiringItems = true),
        User("u2", "Alex Tran", "alex.tran@gmail.com", 5, 140000.0, hasExpiringItems = false),
        User("u3", "Lê Hoàng Lan", "lan.le@gmail.com", 12, 380000.0, hasExpiringItems = true)
    )
}