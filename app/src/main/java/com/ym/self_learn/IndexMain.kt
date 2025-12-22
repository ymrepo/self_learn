package com.ym.self_learn

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ym.learn.home.HomeMain
import com.ym.learn.home.R
import com.ym.learn.player.GlobalAudioPlayer
import com.ym.learn.rewards.RewardsMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val innerNavController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding(),
        bottomBar = {
            Box {
                NavigationBar(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .height(50.dp)
                        .background(color = colorResource(R.color.white))
                ) {
                    BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, item ->

                        NavigationBarItem(
                            icon = {
                                if (index != 1) Icon(
                                    painterResource(item.icon),
                                    contentDescription = null
                                )
                            },
                            label = { Text(item.label, color = Color.DarkGray) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedTextColor = Color.Red, // 使用默认或未选中颜色
                                indicatorColor = Color.Transparent // 移除选中指示器背景
                            ),
                            selected = false,
                            onClick = {
                                innerNavController.navigate(item.route) {
                                    popUpTo(innerNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        painter = painterResource(com.ym.learn.main.R.drawable.ic_home),
                        contentDescription = "",
                    )
                    Text(text = "Scan", color = Color.DarkGray)
                }

            }

        }
    ) { innerPadding ->
        Log.i("padding", "$innerPadding")
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = innerNavController,
                startDestination = Screens.Home.route,


                ) {
                composable(Screens.Home.route) {
                    HomeMain()
                }
                composable(Screens.Rewards.route) { RewardsMain() }
            }
            GlobalAudioPlayer(
                "http://downsc.chinaz.net/Files/DownLoad/sound1/201906/11582.mp3", "title",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 50.dp)
                    .fillMaxWidth(),
            )
        }

    }
}

data class BottomNavigationItem(
    val label: String = "",
    val icon: Int = 0,
    val route: String = ""
) {

    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = com.ym.learn.main.R.drawable.ic_home,
                route = Screens.Home.route
            ),
            BottomNavigationItem(
                label = "",
                icon = 0,
                route = Screens.Scan.route
            ),
            BottomNavigationItem(
                label = "Rewards",
                icon = com.ym.learn.main.R.drawable.ic_home,
                route = Screens.Rewards.route
            ),
        )
    }
}

sealed class Screens(val route: String) {
    object Home : Screens("home_route")
    object Scan : Screens("scan_route")
    object Rewards : Screens("rewards_route")
}