package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.todolist.navigation.NavGraph
import com.example.todolist.navigation.Screens
import com.example.todolist.util.ToDoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            ToDoListTheme {
                NavGraph(
                    startDestination = Screens.TaskListScreen.route,
                    navHostController = navController,
                )
            }
        }
    }
}