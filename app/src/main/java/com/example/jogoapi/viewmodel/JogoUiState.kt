package com.example.jogoapi.viewmodel

import com.example.jogoapi.data.Jogo

sealed interface JogosUiState {

    object Loading : JogosUiState

    data class Success(val jogos: List<Jogo>) : JogosUiState

    object Error : JogosUiState
}