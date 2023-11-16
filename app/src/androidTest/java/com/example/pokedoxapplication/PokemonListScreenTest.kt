package com.example.pokedoxapplication

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokedoxapplication.ui.component.PokemonListScreen
import com.example.pokedoxapplication.ui.viewModel.PokemonListViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokemonListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    // Mock dependencies
    private val mockNavController = TestNavHostController(ApplicationProvider.getApplicationContext())
    private val mockViewModel = mockk<PokemonListViewModel>()

    @Before
    fun setUp() {

    }

    @Test
    fun testPokemonListScreenUI() {

        every { mockViewModel.endReached } returns mutableStateOf(false)
        every { mockViewModel.loadError } returns mutableStateOf("")
        every { mockViewModel.isLoading } returns mutableStateOf(false)
        every { mockViewModel.isSearching } returns mutableStateOf(false)

        // Launch the screen
        composeTestRule.setContent {
            PokemonListScreen(navController = mockNavController, viewModel = mockViewModel)
        }

        // Test the UI elements
        composeTestRule.onNodeWithText("Pokédex").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search for any Pokémon that exists on the planet").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("image description").assertIsDisplayed()

        composeTestRule.onNodeWithTag("searchBarTextField").performTextInput("Pikachu")

    }
}