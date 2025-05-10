package com.example.roomapp.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.roomapp.ui.theme.TodoAppTheme
import com.example.roomapp.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)
        val initialRoute = if (isLoggedIn) "todo" else "login"

        setContent {
            TodoAppTheme(darkTheme = false) {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = initialRoute) {

                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = {
                                    navController.navigate("todo") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onSignUpClick = { navController.navigate("register") }
                            )
                        }

                        composable("register") {
                            RegisterScreen(
                                onSignInClick = { navController.navigate("login") }
                            )
                        }

                        composable("todo") {
                            TodoListPage(
                                viewModel = todoViewModel,
                                onLogout = {
                                    with(sharedPref.edit()) {
                                        clear()
                                        apply()
                                    }
                                    navController.navigate("login") {
                                        popUpTo("todo") { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
