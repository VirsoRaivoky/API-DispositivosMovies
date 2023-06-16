package com.example.dotaapi.ui.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.dotaapi.R
import com.example.dotaapi.viewmodel.JogosViewModel

@Composable
fun JogosDetailScreen(
    navController: NavController,
    jogosViewModel: JogosViewModel
) {
    val uiState by jogosViewModel.detailState.collectAsState()

    BackHandler {
        jogosViewModel.onBackPressed(navController)
    }

    JogoDetail(
        name = uiState.jogoName,
        img = uiState.jogoImg,
        description = uiState.jogoDescription,
        platform = uiState.jogoPlatform,
        developer = uiState.jogoDeveloper
    )
}


@Composable
fun JogoDetail(
    name: String,
    img: String,
    description: String,
    platform: String,
    developer: String,
) {

    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0F) }
    val height = remember { mutableStateOf(0F) }

    Box(Modifier
        .fillMaxSize()
        .background(Color.LightGray)
    )

Column( ) {
    Column(
        modifier = Modifier
            .padding(top = 40.dp)
            .padding(12.dp)
            .height(220.dp)
            .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(img)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RectangleShape)
                .onGloballyPositioned {
                    width.value = it.size.width / density
                    height.value = it.size.height / density
                }
        )
    }
            Text(modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp),
                    fontSize = 25.sp,
                    text = "$name"
            )

            Text(modifier = Modifier
                    .padding(bottom = 10.dp),
                    fontSize = 20.sp,
                    text = "$description"
            )

            Text(modifier = Modifier
                    .padding(bottom = 10.dp),
                    fontSize = 20.sp,
                    text = "Platform: $platform"
            )

            Text(modifier = Modifier
                    .padding(bottom = 10.dp),
                    fontSize = 20.sp,
                    text = "Developer: $developer"
            )
    }
}


