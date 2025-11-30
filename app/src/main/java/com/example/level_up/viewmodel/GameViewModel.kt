package com.example.level_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.model.game.GameDeal
import com.example.level_up.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val repository = GameRepository()

    private val _giveaways = MutableStateFlow<List<GameDeal>>(emptyList())
    val giveaways: StateFlow<List<GameDeal>> = _giveaways

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchGiveaways() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getGiveaways()
            _giveaways.value = result
            _isLoading.value = false
        }
    }
}
