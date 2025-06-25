package com.example.thuc_hanh_navigation_tuan_4.Todo_app.Repository

import com.example.thuc_hanh_navigation_tuan_4.Todo_app.Model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull

interface ProductRepository{
    fun addNewProduct(product: Product)
    fun getProductById(id: String): Product?
    fun getProducts(): StateFlow<List<Product>>
    fun updateProduct(id: String, updatedProduct: Product)
    fun deleteProduct(id: String)
}

class InMemoryProductRepositoty : ProductRepository {
    private val _products = MutableStateFlow<List<Product>>(emptyList())

    override fun addNewProduct(product: Product) {
        _products.value = _products.value + product
    }

    override fun getProductById(id: String): Product? {
        return _products.value.firstOrNull(){ it.id == id }
    }

    override fun getProducts(): StateFlow<List<Product>> {
        return _products
    }

    override fun updateProduct(id: String, updatedProduct: Product) {
        val updatedList = _products.value.map { product ->
            if (product.id == id) updatedProduct else product
        }
        _products.value = updatedList
    }

    override fun deleteProduct(id: String) {
        _products.value = _products.value.filter { it.id != id }
    }
}