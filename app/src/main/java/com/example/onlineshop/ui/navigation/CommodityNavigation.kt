package com.example.onlineshop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.onlineshop.ui.commodity_detail.CommodityDetailScreen
import com.example.onlineshop.ui.commodity_list.CommodityListScreen
import com.example.onlineshop.ui.commodity_list.CommodityListViewModel

sealed class CommodityNavigation(val route: String) {
    object CommodityListDest : CommodityNavigation("commodity_list_dest")
    object CommodityDetailDest : CommodityNavigation("commodity_detail_dest")
}

@Composable
fun OnlineShopNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = CommodityNavigation.CommodityListDest.route
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(CommodityNavigation.CommodityListDest.route) { navBackStackEntry ->
            val parentEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(CommodityNavigation.CommodityListDest.route)
            }
            val parentViewModel = hiltViewModel<CommodityListViewModel>(parentEntry)
            CommodityListScreen(viewModel = parentViewModel) {
                parentViewModel.onCommodityDetail(it)
                navController.navigate(CommodityNavigation.CommodityDetailDest.route, navOptions {
                    launchSingleTop = true
                })
            }
        }

        composable(
            route = CommodityNavigation.CommodityDetailDest.route
        ) { navBackStackEntry ->
            val parentEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(CommodityNavigation.CommodityListDest.route)
            }
            val parentViewModel = hiltViewModel<CommodityListViewModel>(parentEntry)
            CommodityDetailScreen(viewModel = parentViewModel)
        }
    }
}