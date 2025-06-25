package com.example.thuc_hanh_navigation_tuan_4.Todo_app.Repository

import com.example.thuc_hanh_navigation_tuan_4.Todo_app.Model.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface CardRepository {
    fun addCard(card: Card)
    fun getCards(): StateFlow<List<Card>>
    fun moveCard(fromIndex: Int, toIndex: Int)
}

class InMemoryCardRepository: CardRepository{
    private val _cards = MutableStateFlow<List<Card>>(emptyList())

    override fun getCards(): StateFlow<List<Card>> = _cards

    override fun addCard(card: Card) {
        _cards.value = _cards.value + card
    }

    override fun moveCard(fromIndex: Int, toIndex: Int) {
        val currentList = _cards.value.toMutableList()
        val item = currentList.removeAt(fromIndex)
        currentList.add(toIndex, item)
        _cards.value = currentList
    }
}