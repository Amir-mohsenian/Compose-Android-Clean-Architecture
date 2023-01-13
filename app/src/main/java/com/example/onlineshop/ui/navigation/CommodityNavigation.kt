package com.example.onlineshop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.example.onlineshop.R
import com.example.onlineshop.ui.commodity_detail.CommodityDetailScreen
import com.example.onlineshop.ui.commodity_list.CommodityListRoute
import com.example.onlineshop.ui.commodity_list.CommodityListViewModel

sealed class CommodityNavigation(val route: String) {
    object CommodityListDest : CommodityNavigation("commodity_list_dest")
    object CommodityDetailDest : CommodityNavigation("commodity_detail_dest")
}

@Composable
fun OnlineShopNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = CommodityNavigation.CommodityListDest.route,
    onPageChange: (title: String) -> (Unit)
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        CommodityNavigation.CommodityListDest.route -> {
            onPageChange(stringResource(id = R.string.title_commodity_page))
        }
    }

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
            CommodityListRoute(viewModel = parentViewModel) {
                parentViewModel.onCommodityDetail(it)
                onPageChange(it.title)
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