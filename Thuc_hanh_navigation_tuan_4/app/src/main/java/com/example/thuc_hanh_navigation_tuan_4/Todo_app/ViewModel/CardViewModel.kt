package com.example.thuc_hanh_navigation_tuan_4.Todo_app.ViewModel

import androidx.lifecycle.ViewModel
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.Model.Card
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.Repository.CardRepository
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.Repository.InMemoryCardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CardViewModel( private val repository: CardRepository = InMemoryCardRepository()) : ViewModel() {
    val cards: StateFlow<List<Card>> = repository.getCards()

    fun addCard(task: String, description: String){
        val card = Card(task = task, description = description)
        repository.addCard(card)
    }

    fun moveCard(fromIndex: Int, toIndex: Int){
        repository.moveCard(fromIndex = fromIndex, toIndex = toIndex)
    }
}