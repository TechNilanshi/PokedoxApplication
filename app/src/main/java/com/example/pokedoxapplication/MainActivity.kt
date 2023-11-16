package com.example.pokedoxapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedoxapplication.ui.component.PokemonDetailScreen
import com.example.pokedoxapplication.ui.component.PokemonFilterScreen
import com.example.pokedoxapplication.ui.component.PokemonListScreen
import com.example.pokedoxapplication.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

/**
 * This class is the entry point of the application
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "pokemon_list_screen",
                ) {
                    composable("pokemon_list_screen") {
                        PokemonListScreen(navController = navController)
                    }
                    composable(
                        "pokemon_detail_screen/{dominantColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            },
                        ),
                    ) {
                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }
                        PokemonDetailScreen(
                            pokemonName = pokemonName?.lowercase(Locale.ROOT) ?: "",
                            navController = navController,
                        )
                    }
                    composable("pokemon_filter_screen") {
                        PokemonFilterScreen(navController = navController)
                    }
                }
            }
        }
    }
}