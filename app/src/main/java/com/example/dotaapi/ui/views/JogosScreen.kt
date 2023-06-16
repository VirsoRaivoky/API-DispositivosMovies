package com.example.dotaapi.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dotaapi.R
import com.example.dotaapi.data.Jogo
import com.example.dotaapi.viewmodel.JogosUiState
import com.example.dotaapi.viewmodel.JogosViewModel

@Composable
fun JogosScreen(
    navController: NavController,
    jogosViewModel: JogosViewModel,
){
    val uiState by jogosViewModel.uiState.collectAsState()

    when(uiState){
        is JogosUiState.Loading -> LoadingScreen()
        is JogosUiState.Success -> JogosList((uiState as JogosUiState.Success).jogos, showDetail = {jogosViewModel.showDetail(it, navController)})
        is JogosUiState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.loading),
            contentDescription = ("tela de loading")
        )
    }
}

@Composable
fun ErrorScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.error),
            contentDescription = ("tela de erro")
        )
    }
}


@Composable
fun JogosList(
    jogos: List<Jogo>,
    showDetail: (Jogo) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        columns = GridCells.Fixed(2)
    ){
        items(jogos){ jogo ->
            JogoEntry(jogo = jogo, showDetail = {showDetail(jogo)})

        }
    }
}


@Composable
fun JogoEntry(
    jogo: Jogo,
    showDetail: () -> Unit
) {
    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0F)}
    val height = remember { mutableStateOf(0F)}

    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { showDetail() },
        elevation = CardDefaults.cardElevation(9.dp)
    ){
        Box() {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(jogo.img)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = jogo.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RectangleShape)
                    .onGloballyPositioned {
                        width.value = it.size.width / density
                        height.value = it.size.height / density
                    }
            )
            Box(modifier = Modifier
                .size(
                    width = width.value.dp,
                    height = height.value.dp
                )
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black),
                        200f,
                        280f
                    )
                )
            ){

            }
            Text(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = jogo.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp
                )
            )
        }
    }
}