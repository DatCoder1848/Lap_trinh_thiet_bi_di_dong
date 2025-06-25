package com.example.thuc_hanh_navigation_tuan_4.Todo_app.ViewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.Model.Product
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.Repository.InMemoryProductRepositoty
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.Repository.ProductRepository
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel ( val repository: ProductRepository = InMemoryProductRepositoty() ): ViewModel() {
    val products: StateFlow<List<Product>> = repository.getProducts()

    fun addNewProduct(name: String, price: Long, description: String){
        val product = Product(name = name, price = price, description = description)
        repository.addNewProduct(product)
    }

    fun getProductById(id: String): Product? {
        return repository.getProductById(id)
    }

    fun getProducts(): List<Product> {
        return products.value
    }
}