package com.example.thuc_hanh_navigation_tuan_4.Todo_app.Model

import java.util.UUID

data class Card(
    val id: String = UUID.randomUUID().toString(),
    val task: String,
    val description: String
)