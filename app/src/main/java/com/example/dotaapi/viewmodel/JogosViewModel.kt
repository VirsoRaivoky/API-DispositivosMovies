package com.example.dotaapi.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.dotaapi.data.Jogo
import com.example.dotaapi.network.JogoApi
import com.example.dotaapi.ui.views.JogosDetailScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class JogosViewModel: ViewModel() {

    private var _telaInicialUiState: MutableStateFlow<TelaInicialUiState> = MutableStateFlow(
        TelaInicialUiState()
    )

    private var _jogoUiState: MutableStateFlow<JogosUiState> =
        MutableStateFlow(JogosUiState.Loading)
    val uiState: StateFlow<JogosUiState> = _jogoUiState.asStateFlow()

    private var _jogoDetailScreenUiStateUiState: MutableStateFlow<JogoDetailScreenUiState> = MutableStateFlow(JogoDetailScreenUiState())
    val detailState: StateFlow<JogoDetailScreenUiState> = _jogoDetailScreenUiStateUiState.asStateFlow()

    var showDetail: Boolean = false

    init {
        getJogos()
    }

    private fun getJogos() {
        viewModelScope.launch {
            try {
                _jogoUiState.value = JogosUiState.Success(JogoApi.retrofitService.getJogos())
            } catch (e: IOException) {
                _jogoUiState.value = JogosUiState.Error
            } catch (e: HttpException) {
                _jogoUiState.value = JogosUiState.Error
            }
        }
    }

    fun navigate(navController: NavController) {
        if (_telaInicialUiState.value.screenName == "Jogos") {
            if (showDetail) {
                _telaInicialUiState.update { currentState ->
                    currentState.copy(
                        screenName = "Jogo Detail"

                    )
                }
            }
            navController.navigate("detail_screen")
        } else {
            _telaInicialUiState.update { currentState ->
                currentState.copy(
                    screenName = "Jogos"
                )
            }
            _jogoDetailScreenUiStateUiState.update {
                JogoDetailScreenUiState()
            }
            navController.navigate("jogo_screen"){
                popUpTo("jogo_screen"){
                    inclusive = true
                }
            }
        }
    }

    fun showDetail(jogo: Jogo, navController: NavController) {
        showDetail = true
        _jogoDetailScreenUiStateUiState.update { currentState ->
            currentState.copy(
                jogoName = jogo.name,
                jogoImg = jogo.img,
                jogoDescription = jogo.description,
                jogoPlatform = jogo.platform,
                jogoDeveloper = jogo.developer,
            )
        }
        navigate(navController)
    }

    fun onBackPressed(navController: NavController){
        showDetail = false
        _telaInicialUiState.update {currentState ->
            currentState.copy(
                screenName = "Jogos"
            )
        }
        navController.popBackStack()
    }
}
