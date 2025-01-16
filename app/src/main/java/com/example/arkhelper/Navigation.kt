package com.example.arkhelper

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.arkhelper.ui.ArkWikiScreen
import com.example.arkhelper.ui.DetailedCreatureCard
import com.example.arkhelper.ui.KnockoutCalculatorscreen

import com.example.arkhelper.ui.SettingsScreen
import com.example.arkhelper.ui.StatsCalculatorscreen


object Routes {
    const val SCREEN_ALL_CREATURES = "CreatureList"
    const val SCREEN_CREATURE_DETAILS = "DetailedCreature/{creatureId}"
    const val SCREEN_KNOCKOUT_CALCULATOR="KnockoutCalculator"
    const val SCREEN_STATS_CALCULATOR="StatsCalculator"
    const val SCREEN_SERVER_SETTINGS="ServerSettins"
    fun getCreatureDetailsPath(creatureId: Int?) : String {
        if (creatureId != null && creatureId != -1) {
            return "DetailedCreature/$creatureId"
        }
        return "DetailedCreature/0"
    }
}
@Composable
fun NavigationController(viewModel: CreatureViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SCREEN_ALL_CREATURES) {
        composable(Routes.SCREEN_ALL_CREATURES) {
            ArkWikiScreen(viewModel = viewModel,navigation=navController)

        }
        composable(
            Routes.SCREEN_CREATURE_DETAILS,
            arguments = listOf(
                navArgument("creatureId") {
                    type = NavType.IntType
                }
            )
        ) {backStackEntry ->
            backStackEntry.arguments?.getInt("creatureId")?.let {
                DetailedCreatureCard(
                    viewModel = viewModel,
                    navigation = navController,
                    creatureId = it
                )
            }
        }
        composable(Routes.SCREEN_KNOCKOUT_CALCULATOR){
            KnockoutCalculatorscreen(viewModel = viewModel,navigation=navController)
        }
        composable(Routes.SCREEN_STATS_CALCULATOR){
            StatsCalculatorscreen(viewModel = viewModel,navigation=navController)
        }
        composable(Routes.SCREEN_SERVER_SETTINGS){
            SettingsScreen(viewModel = viewModel, navigation = navController,onSaveSettings = { updatedSettings ->viewModel.updateSettings(updatedSettings) })
        }
    }
}