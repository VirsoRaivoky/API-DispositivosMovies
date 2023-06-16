package com.example.dotaapi.viewmodel

import com.example.dotaapi.data.Jogo

sealed interface JogosUiState {

    object Loading : JogosUiState

    data class Success(val jogos: List<Jogo>) : JogosUiState

    object Error : JogosUiState
}