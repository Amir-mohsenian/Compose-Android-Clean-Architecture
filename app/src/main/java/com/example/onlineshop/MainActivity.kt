package com.example.onlineshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.onlineshop.ui.navigation.OnlineShopNavHost
import com.example.onlineshop.ui.theme.OnlineShopTheme
import com.example.onlineshop.ui.theme.Yellow500
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnlineShopTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    var topAppbarText by remember {
        mutableStateOf("")
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(text = topAppbarText, color = Yellow500)
            }, backgroundColor = MaterialTheme.colors.primary, elevation = 10.dp)
        }) {
            OnlineShopNavHost(navController = navController) {
                topAppbarText = it
            }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OnlineShopTheme {
        MainScreen()
    }
}