package com.ovais.inspiration.core.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.ovais.inspiration.core.navigation.InspirationNavigator
import com.ovais.inspiration.core.ui.theme.InspirationTheme

class InspirationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InspirationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    InspirationNavigator(
                        paddingValues = innerPadding
                    )
                }
            }
        }
    }
}
