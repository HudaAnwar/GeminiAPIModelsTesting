package com.example.geminiapimodelstesting

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.geminiapimodelstesting.chat.ChatRoute
import com.example.geminiapimodelstesting.text.TextRoute
import com.example.geminiapimodelstesting.textandimage.TextAndImageRoute
import com.example.geminiapimodelstesting.ui.theme.GeminiAPIModelsTestingTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeminiAPIModelsTestingTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { BottomBar(navController) },
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    NavHost(navController, startDestination = NavigationDestination.Text.route) {
                        composable(NavigationDestination.Text.route) { TextRoute() }
                        composable(NavigationDestination.TextAndImage.route) { TextAndImageRoute() }
                        composable(NavigationDestination.Chat.route) { ChatRoute() }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val screens = NavigationDestination.values()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = Color.LightGray),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        screens.forEachIndexed { index, screen ->
            val isSelected = navController.currentDestination?.route == screen.route
            Row(modifier = Modifier.padding(6.dp)) {
                Box(
                    modifier = Modifier
                        .clickable { navController.navigate(screen.route) }
                        .background(if (isSelected) Color.Gray else Color.Transparent)
                        .padding(end = 28.dp)
                ) {
                    Text(screen.name, color = if (isSelected) Color.White else Color.Black)
                }
                if (index != screens.lastIndex) {
                    Divider(
                        modifier = Modifier
                            .width(2.dp)
                            .height(24.dp),
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

