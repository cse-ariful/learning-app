package com.online.learning.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.online.learning.R
import com.online.learning.features.home.dashboard.DashBoardScreen
import com.online.learning.ui.theme.ELearningTheme
import kotlinx.coroutines.launch

enum class BottomNavItem(val icon: Int, val route: String, val title: String) {
    HOME(R.drawable.home, "home", "Home"), COURSES(R.drawable.cardic_courses, "courses", "Course"), BOOKS(
        R.drawable.ic_books,
        "books",
        "Books"
    ),
    SETTING(R.drawable.ic_settings, "setting", "Setting"),
}

@Composable
fun HomeScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val homeNavController = rememberNavController()
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.White,
        drawerContent = {
            NavDrawer()
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            NavHost(
                navController = homeNavController,
                startDestination = BottomNavItem.HOME.route,
                modifier = Modifier
            ) {
                composable(BottomNavItem.HOME.route) {
                    DashBoardScreen(navController) {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                }
                composable(BottomNavItem.BOOKS.route) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Red)
                    )
                }
                composable(BottomNavItem.COURSES.route) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Green)
                    )
                }
                composable(BottomNavItem.SETTING.route) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Yellow)
                    )
                }
            }
            BottomNavBar(homeNavController)
        }
    }
}

@Composable
fun BoxScope.BottomNavBar(navController: NavController) {
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .shadow(4.dp, shape = RoundedCornerShape(24.dp), spotColor = Color(0x3D485B0F)),
        backgroundColor = Color.White,


        ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        BottomNavItem.values().forEach {
            BottomNavItem(it, isActive = currentRoute == it.route) {
                navController.navigate(it.route)
            }
        }


    }
}

@Composable
fun RowScope.BottomNavItem(item: BottomNavItem, isActive: Boolean, onclick: () -> Unit) {

    Box(
        modifier = Modifier
            .size(44.dp)
            .align(Alignment.CenterVertically)
            .background(shape = CircleShape, color = if (isActive) Color(0xff4673FF) else Color.Unspecified)
            .clip(CircleShape)
            .clickable {
                onclick()
            }, contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = item.icon),
            contentDescription = "",
            tint = if (isActive) Color.White else Color(0xffD0D7DC),
        )
    }

}


@Composable
fun NavDrawer() {
    Box(modifier = Modifier.fillMaxWidth(0.6f)) {

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    ELearningTheme() {
//        BuildCourseItem(LocalConfiguration.current)
        HomeScreen(navController = navController)
    }
}