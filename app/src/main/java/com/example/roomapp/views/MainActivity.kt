package com.example.roomapp.views

import com.google.firebase.FirebaseApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
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
        FirebaseApp.initializeApp(this)
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        setContent {
            TodoAppTheme {
                val navController = rememberNavController()

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = { /* tutaj możesz nawigować do TodoListPage */ },
                                onSignUpClick = { navController.navigate("register") }
                            )
                        }
                        composable("register") {
                            RegisterScreen(onSignInClick = { navController.navigate("login") })
                        }
                    }
                }
            }
        }
    }
}
