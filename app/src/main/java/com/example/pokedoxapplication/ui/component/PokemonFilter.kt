package com.example.pokedoxapplication.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokedoxapplication.R
import com.example.pokedoxapplication.utils.FilterDataProvider

/**
 * This function is responsible to provide the UI for pokemon filter using compose
 */
@Composable
fun PokemonFilterScreen(navController: NavHostController) {
    Column {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 28.dp, end = 25.dp, top = 30.dp)
                .fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.filters),
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight(800),
                        color = Color(0xFF2E3156),
                    ),
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(id = R.drawable.actions),
                    contentDescription = stringResource(id = R.string.image_description),
                    contentScale = ContentScale.None,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
            }
            Divider(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color(0x262E3156))
            )
            PokemonTypeFilter()
            PokemonGenderFilter()
            PokemonStatFilter()
        }

        Divider(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color(0x262E3156))
        )
        Row(
            modifier = Modifier
                .shadow(
                    elevation = 14.dp,
                    spotColor = Color(0x662E3156),
                    ambientColor = Color(0x662E3156)
                )
                .height(67.dp)
                .background(color = Color(0xFFFFFFFF)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 22.dp)
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF2E3156),
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .height(37.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.reset),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF2E3156),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 17.dp, end = 19.dp)
                    .weight(1f)
                    .height(37.dp)
                    .clickable {
                        navController.popBackStack()
                    }
                    .background(color = Color(0xFF2E3156), shape = RoundedCornerShape(size = 8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.apply),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}

@Composable
fun PokemonTypeFilter() {
    val isFilterOpen = remember { mutableStateOf(false) }
    val image = remember {
        mutableStateOf(R.drawable.vector)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 28.dp)
    ) {

        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF2E3156),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .fillMaxWidth()

        ) {
            Column {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 14.dp, bottom = 14.dp)
                ) {
                    Text(
                        text = stringResource(R.string.type),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFF2E3156),
                        ), modifier = Modifier.padding(start = 20.dp)
                    )

                    Divider(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .width(1.dp)
                            .height(31.dp)
                            .background(color = Color(0x262E3156))
                    )

                    Text(
                        text = stringResource(R.string.normal_5_more),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(300),
                            color = Color(0xFF2E3156),
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(image.value),
                        contentDescription = stringResource(id = R.string.image_description),
                        contentScale = ContentScale.None,
                        modifier = Modifier
                            .padding(end = 26.dp)
                            .clickable {
                                isFilterOpen.value = !isFilterOpen.value
                            }
                    )
                }
                if (isFilterOpen.value) {
                    image.value = R.drawable.vector_minus
                    PokemonTypeFilterData()
                } else {
                    image.value = R.drawable.vector
                    EmptyView()
                }
            }
        }
    }
}

@Composable
fun PokemonGenderFilter() {
    val isFilterOpen = remember { mutableStateOf(true) }
    val image = remember {
        mutableStateOf(R.drawable.vector)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 28.dp)
    ) {

        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF2E3156),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .fillMaxWidth()

        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 14.dp, bottom = 14.dp)
                ) {
                    Text(
                        text = stringResource(R.string.gender),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFF2E3156),
                        ), modifier = Modifier.padding(start = 20.dp)
                    )

                    Divider(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .width(1.dp)
                            .height(31.dp)
                            .background(color = Color(0x262E3156))
                    )

                    Text(
                        text = stringResource(R.string.normal_5_more),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(300),
                            color = Color(0xFF2E3156),
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(image.value),
                        contentDescription = stringResource(id = R.string.image_description),
                        contentScale = ContentScale.None,
                        modifier = Modifier
                            .padding(end = 26.dp)
                            .clickable {
                                isFilterOpen.value = !isFilterOpen.value
                            }
                    )
                }
                if (isFilterOpen.value) {
                    image.value = R.drawable.vector_minus
                    PokemonGenderFilterData()
                } else {
                    image.value = R.drawable.vector
                    EmptyView()
                }
            }
        }
    }
}

@Composable
fun PokemonStatFilter() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 28.dp)
    ) {

        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF2E3156),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .fillMaxWidth()

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 14.dp, bottom = 14.dp)
            ) {
                Text(
                    text = stringResource(R.string.stats),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight(800),
                        color = Color(0xFF2E3156),
                    ), modifier = Modifier.padding(start = 20.dp)
                )

                Divider(
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .width(1.dp)
                        .height(31.dp)
                        .background(color = Color(0x262E3156))
                )

                Text(
                    text = stringResource(R.string.normal_5_more),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(300),
                        color = Color(0xFF2E3156),
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.vector),
                    contentDescription = stringResource(id = R.string.image_description),
                    contentScale = ContentScale.None,
                    modifier = Modifier
                        .padding(end = 26.dp)
                )
            }
        }
    }
}

@Composable
fun PokemonTypeFilterData() {
    Column(
        modifier = Modifier
            .padding(top = 5.dp, start = 20.dp, end = 26.dp)
    ) {

        Divider(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color(0x262E3156))
        )

        val typeList = FilterDataProvider.getTypeFilterData()
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), modifier = Modifier
                .padding(top = 18.dp)
        ) {
            items(typeList.size) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val checkedState = remember { mutableStateOf(false) }
                    Checkbox(
                        checked = checkedState.value,
                        modifier = Modifier.padding(5.dp),
                        onCheckedChange = { checkedState.value = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF2E3156),
                            uncheckedColor = Color(0xFF2E3156),
                            checkmarkColor = Color(0xFFFFFFFF)
                        )
                    )
                    Text(
                        text = typeList[it],
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2E3156),
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonGenderFilterData() {
    Column(
        modifier = Modifier
            .padding(top = 5.dp, start = 20.dp, end = 26.dp)
    ) {

        Divider(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color(0x262E3156))
        )

        val typeList = FilterDataProvider.getGenderFilterData()
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), modifier = Modifier
                .padding(top = 18.dp)
        ) {
            items(typeList.size) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val checkedState = remember { mutableStateOf(false) }
                    Checkbox(
                        checked = checkedState.value,
                        modifier = Modifier.padding(5.dp),
                        onCheckedChange = { checkedState.value = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF2E3156),
                            uncheckedColor = Color(0xFF2E3156),
                            checkmarkColor = Color(0xFFFFFFFF)
                        )
                    )
                    Text(
                        text = typeList[it],
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2E3156),
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyView() {}
