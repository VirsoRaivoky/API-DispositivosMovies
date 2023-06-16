package com.example.jogoapi.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jogoapi.viewmodel.JogosViewModel

@Composable
fun TelaInicial(
    jogosViewModel: JogosViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "jogo_screen"){
        composable("jogo_screen"){
            JogosScreen(navController = navController, jogosViewModel = jogosViewModel)
        }
        composable("detail_screen"){
            JogosDetailScreen(navController = navController, jogosViewModel = jogosViewModel )
        }
    }
}