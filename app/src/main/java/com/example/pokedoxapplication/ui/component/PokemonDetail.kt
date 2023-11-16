package com.example.pokedoxapplication.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.privacysandbox.tools.core.model.Type
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedoxapplication.R
import com.example.pokedoxapplication.data.remote.DoubleDamage
import com.example.pokedoxapplication.data.remote.Pokemon
import com.example.pokedoxapplication.data.remote.PokemonSpecies
import com.example.pokedoxapplication.ui.viewModel.PokemonDetailViewModel
import com.example.pokedoxapplication.ui.viewModel.PokemonListViewModel
import com.example.pokedoxapplication.utils.Constants.IMAGE_BASE_URL
import com.example.pokedoxapplication.utils.Resource
import kotlin.math.roundToInt

/**
 * This function is responsible to provide the UI for pokemon details using compose
 */
@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    navController: NavController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = Color(0xFFDEEDED))
            .padding(bottom = 16.dp)
    ) {
        PokemonDetailTopSection(
            navController = navController,
            pokemonInfo = pokemonInfo
        )

        when (pokemonInfo) {
            is Resource.Success -> {
                val pokemonSpecies =
                    produceState(initialValue = Resource.Loading()) {
                        value = pokemonInfo.data?.id?.let { viewModel.getPokemonSpecies(it) }!!
                    }.value

                PokemonDetailImageSection(pokemonInfo, pokemonSpecies)
                PokemonDetailDataSection(
                    pokemonWeight = pokemonInfo.data!!.weight,
                    pokemonHeight = pokemonInfo.data.height
                )
                PokemonDetailDataSectionGender(pokemonInfo.data, pokemonSpecies)
                PokemonBaseStats(pokemonInfo = pokemonInfo.data)

            }

            else -> {}
        }
    }
}

@Composable
fun PokemonDetailImageSection(
    pokemonInfo: Resource<Pokemon>,
    pokemonSpecies: Resource<PokemonSpecies>,
    viewModel: PokemonListViewModel = hiltViewModel(),
) {
    var showPopup by remember { mutableStateOf(false) }
    val description =
        pokemonSpecies.data?.flavor_text_entries?.joinToString(separator = " ") { it.flavor_text }
    Row(Modifier.padding(top = 27.dp, start = 28.dp, end = 28.dp)) {
        val defaultDominantColor = MaterialTheme.colors.surface
        var dominantColor by remember {
            mutableStateOf(defaultDominantColor)
        }
        val stroke = Stroke(
            width = 2f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
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
        ) {
            Column {
                val url =
                    "$IMAGE_BASE_URL${pokemonInfo.data?.id}.png"
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .build(),
                    contentDescription = pokemonInfo.data?.name,
                    modifier = Modifier
                        .padding(1.dp)
                        .width(125.44221.dp)
                        .height(123.04522.dp)
                        .align(Alignment.CenterHorizontally),
                    onSuccess = { success ->
                        val drawable = success.result.drawable
                        viewModel.calcDominantColor(drawable) { color ->
                            dominantColor = color
                        }
                    }
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 24.dp)
                .height(229.dp)
        ) {
            description?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 25.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2E3156),
                    ),
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Text(
                text = stringResource(R.string.read_more),
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF2E3156),
                ),
                modifier = Modifier.clickable {
                    showPopup = true
                }
            )
        }
    }

    if (showPopup) {
        Popup(alignment = Alignment.Center, onDismissRequest = { showPopup = false }) {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 20.dp,
                        spotColor = Color(0xFF2E3156),
                        ambientColor = Color(0xFF2E3156)
                    )
                    .width(250.dp)
                    .height(438.dp)
                    .background(
                        color = Color(0xFF2E3156),
                        shape = RoundedCornerShape(size = 8.dp),
                    ),

                ) {
                Column {
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter = painterResource(id = R.drawable.cross),
                            contentDescription = stringResource(id = R.string.image_description),
                            contentScale = ContentScale.None,
                            modifier = Modifier
                                .padding(end = 10.dp, top = 10.dp)
                                .width(25.dp)
                                .height(25.dp)
                                .clickable {
                                    showPopup = false
                                }
                        )
                    }
                    val scroll = rememberScrollState(0)

                    if (description != null) {
                        Text(
                            text = description,
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 18.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                            ),
                            modifier = Modifier
                                .padding(20.dp)
                                .verticalScroll(scroll)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonDetailTopSection(
    navController: NavController,
    pokemonInfo: Resource<Pokemon>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 10.dp, start = 28.dp, end = 28.dp)
    ) {
        Column(Modifier.weight(1f)) {
            pokemonInfo.data?.name?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight(800),
                        color = Color(0xFF2E3156),
                        letterSpacing = 1.8.sp,
                    )
                )
            }
            Text(
                text = String.format("%03d", pokemonInfo.data?.id),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF2E3156),
                    letterSpacing = 1.8.sp,
                )
            )
        }
        Image(
            painter = painterResource(id = R.drawable.actions),
            contentDescription = stringResource(id = R.string.image_description),
            contentScale = ContentScale.None,
            modifier = Modifier
                .padding(0.dp)
                .width(25.dp)
                .height(25.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun PokemonDetailDataItem(
    dataValue: Float,
    dataUnit: String,
    type: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = type,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF2E3156),
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$dataValue$dataUnit",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF2E3156),
            )
        )
    }
}

@Composable
fun PokemonDetailDataItemGender(
    dataValue: String,
    type: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = type,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF2E3156),
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = dataValue,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF2E3156),
            )
        )
    }
}

@Composable
fun PokemonDetailDataItemTypes(
    dataValue: List<Any>,
    type: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = type,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF2E3156),
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        val list: List<Type> = dataValue as List<Type>
        Row {
            for (i in list.indices) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFF2E3156),
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                        .background(
                            color = Color(0xFFE6C5C9),
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                )
                {
                    //text = list[i].type.name,

                    Text(
                        text = "Pokemon",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 25.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2E3156),
                        ),
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonDetailDataItemWeakAgainst(
    dataValue: List<Any>,
    type: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = type,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF2E3156),
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        val list: List<DoubleDamage> = dataValue as List<DoubleDamage>
        Row {
            for (i in list.indices) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFF2E3156),
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                        .background(
                            color = Color(0xFFE6C5C9),
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                )
                {
                    Text(
                        text = list[i].name,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 25.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2E3156),
                        ),
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonDetailDataSection(
    pokemonWeight: Int,
    pokemonHeight: Int
) {
    val pokemonWeightInKg = remember {
        pokemonWeight.times(100f).roundToInt() / 1000f
    }
    val pokemonHeightInMeters = remember {
        pokemonHeight.times(100f).roundToInt() / 1000f
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 28.dp, end = 28.dp)
    ) {
        PokemonDetailDataItem(
            dataValue = pokemonHeightInMeters,
            dataUnit = stringResource(R.string.m),
            type = stringResource(R.string.height),
            modifier = Modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier
                .size(1.dp, pokemonHeight.dp)
                .background(Color.LightGray)
        )
        PokemonDetailDataItem(
            dataValue = pokemonWeightInKg,
            dataUnit = stringResource(R.string.kg),
            type = stringResource(R.string.weight),
            modifier = Modifier.weight(1f)
        )

    }
}

@Composable
fun PokemonDetailDataSectionGender(
    pokemonInfo: Pokemon,
    pokemonSpecies: Resource<PokemonSpecies>,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 28.dp, end = 28.dp)
    ) {
        PokemonDetailDataItemGender(
            dataValue = pokemonInfo.name,
            type = stringResource(R.string.gender_s),
            modifier = Modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier
                .size(1.dp)
                .background(Color.LightGray)
        )
        val eggGroups = pokemonSpecies.data?.egg_groups?.joinToString(separator = ", ") { it.name }

        if (eggGroups != null) {
            PokemonDetailDataItemGender(
                dataValue = eggGroups,
                type = stringResource(R.string.egg_groups),
                modifier = Modifier.weight(1f)
            )
        }

    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 28.dp, end = 28.dp)
    ) {
        val commaSeperatedString =
            pokemonInfo.abilities.joinToString(separator = ", ") { it.ability.name }

        PokemonDetailDataItemGender(
            dataValue = commaSeperatedString,
            type = stringResource(R.string.abilities),
            modifier = Modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier
                .size(1.dp)
                .background(Color.LightGray)
        )
        PokemonDetailDataItemTypes(
            dataValue = pokemonInfo.types,
            type = stringResource(R.string.types),
            modifier = Modifier.weight(1f)
        )

    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 28.dp, end = 28.dp)
    ) {
        val pokemonTypes =
            produceState(initialValue = Resource.Loading()) {
                value = pokemonInfo.id.let { viewModel.getPokemonType(it) }
            }.value
        pokemonTypes.data?.damage_relations?.double_damage_from?.let {
            PokemonDetailDataItemWeakAgainst(
                dataValue = it,
                type = stringResource(R.string.weak_against),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue / statMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(
            animDuration,
            animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Row(modifier = Modifier.padding(top = 15.dp)) {
        Text(
            text = statName,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF2E3156),

                ),
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
                .height(height)
                .clip(RectangleShape)
                .background(
                    if (isSystemInDarkTheme()) {
                        Color(0xFF93B2B2)
                    } else {
                        Color.LightGray
                    }
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(curPercent.value)
                    .clip(RectangleShape)
                    .background(Color(0xFF2E3156))
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = (curPercent.value * statMaxValue).toInt().toString(),
                    style = TextStyle(
                        fontSize = 10.sp,
                        lineHeight = 25.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFFFFFFFF),

                        )
                )
            }
        }
    }

}

@Composable
fun PokemonBaseStats(
    pokemonInfo: Pokemon,
    animDelayPerItem: Int = 100
) {
    val maxBaseStat = remember {
        pokemonInfo.stats.maxOf { it.base_stat }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 28.dp, end = 28.dp)
    ) {
        Text(
            text = "Stats",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF2E3156)
            )
        )
        Spacer(modifier = Modifier.height(4.dp))

        for (i in pokemonInfo.stats.indices) {
            val stat = pokemonInfo.stats[i]
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}