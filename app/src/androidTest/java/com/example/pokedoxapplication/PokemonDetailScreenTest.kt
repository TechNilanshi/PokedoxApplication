package com.example.pokedoxapplication

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pokedoxapplication.ui.component.PokemonDetailScreen
import com.example.pokedoxapplication.ui.viewModel.PokemonDetailViewModel
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokemonDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController
    @MockK
    private lateinit var viewModel: PokemonDetailViewModel
    @Before
    fun setup() {
        navController = TestNavHostController(
            InstrumentationRegistry.getInstrumentation().targetContext
        )
    }

    @Test
    fun testPokemonDetailScreen() {
        // Load the PokemonDetailScreen with required data

        composeTestRule.setContent {
            val navController = rememberNavController()
            PokemonDetailScreen(pokemonName = "Pikachu", navController = navController)
        }

        // Perform UI testing actions and assertions
        composeTestRule.onNodeWithText("Pikachu").assertIsDisplayed()
        composeTestRule.onNodeWithText("Height").assertIsDisplayed()
        composeTestRule.onNodeWithText("Weight").assertIsDisplayed()
    }
}