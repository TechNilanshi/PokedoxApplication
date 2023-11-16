package com.example.pokedoxapplication.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedoxapplication.R
import com.example.pokedoxapplication.data.model.PokemonListEntry
import com.example.pokedoxapplication.ui.viewModel.PokemonListViewModel

/**
 * This function is responsible to provide the UI for pokemon list using compose
 */
@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
){
    Surface(
        color = Color(0xFFDEEDED),
        modifier = Modifier.fillMaxSize()
    ){
        Column(modifier = Modifier.padding(start = 28.dp, end = 25.dp)) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.title_pok_dex),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF2E3156),

                    letterSpacing = 1.8.sp,
                ),
                modifier = Modifier
                    .width(128.dp)
                    .height(35.dp)
            )
            Divider(modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp)
                .width(318.dp)
                .height(1.dp)
                .background(color = Color(0xFF5D5F7E)))

            Text(
                text = stringResource(R.string.sub_title),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF5D5F7E),

                    )
            )

            Row (modifier = Modifier.padding(top = 16.dp)){
                SearchBar(
                    hint = stringResource(R.string.hint_name_or_number),
                    modifier = Modifier
                        .width(239.dp)
                        .padding(end = 16.dp)
                        .weight(1f)
                ){
                    viewModel.searchPokemonList(it)
                }

                Image(
                    painter = painterResource(id = R.drawable.filter_icon),
                    contentDescription = stringResource(R.string.image_description),
                    contentScale = ContentScale.None,
                    modifier = Modifier
                        .width(76.66666.dp)
                        .height(41.dp)
                        .clickable {
                            navController.navigate(
                                "pokemon_filter_screen"
                            )
                        }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(navController = navController)
        }
    }
}
@Preview
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch:(String) -> Unit = {}
){
    var text by remember{
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, shape = RoundedCornerShape(size = 8.dp))
                .background(color = Color(0xFFC9DDE2), shape = RoundedCornerShape(size = 8.dp))
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused && text.isNotEmpty()
                }
        )
        if(isHintDisplayed){
            Text(
                text = stringResource(id = R.string.hint_name_or_number),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0x805D5F7E),
                )
            )
        }
    }
}
@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
){
    val pokemonList by remember { viewModel.pokemonList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    LazyColumn {
        val itemCount = if(pokemonList.size % 2 == 0){
            pokemonList.size / 2
        } else{
            pokemonList.size / 2 + 1
        }
        items(itemCount){
            if(it >= itemCount - 1 && !endReached && !isLoading && !isSearching){
                LaunchedEffect(key1 = true){
                    viewModel.loadPokemonPaginated()
                }
            }
            PokedexRow(rowIndex = it, entries = pokemonList, navController = navController)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if(loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadPokemonPaginated()
            }
        }
    }
}

@Composable
fun PokedexEntry(
    entry: PokemonListEntry.PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }
    val stroke = Stroke(width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(0.7f)
            .drawBehind {
                drawRoundRect(
                    color = Color(0xFF2E3156), style = stroke,
                    cornerRadius = CornerRadius(10f, 10f)
                )
            }
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    ),
                )
            )
            .clickable {
                navController.navigate(
                    "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.name}"
                )
            }
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.url)
                    .build(),
                contentDescription = entry.name,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                onSuccess = { success ->
                    val drawable = success.result.drawable
                    viewModel.calcDominantColor(drawable) { color ->
                        dominantColor = color
                    }
                }
            )
            // Remaining Task
            // Choose dominant color
            Text(
                text = entry.name,
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF2E3156),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            )
            Text(
                text = String.format("%03d", entry.number),
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF2E3156),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun PokedexRow(
    rowIndex: Int,
    entries: List<PokemonListEntry.PokedexListEntry>,
    navController: NavController
) {
    Column {
        Row {
            PokedexEntry(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if(entries.size >= rowIndex * 2 + 2) {
                PokedexEntry(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.button_retry))
        }
    }
}