package com.example.thuc_hanh_navigation_tuan_4.Todo_app.Model

import com.example.thuc_hanh_navigation_tuan_4.R

data class Product(
    val id: String = ProductIdGenerator.createId(),
    val image: Int = R.drawable.main_logo,
    val name: String,
    val price: Long,
    val description: String
)


object ProductIdGenerator{
    private var id = 0

    fun createId(): String {
        return "Prd_${++id}"
    }
}