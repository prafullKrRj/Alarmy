package com.prafull.alarmy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.prafull.alarmy.ui.theme.AlarmyTheme
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val uid = UUID.randomUUID()
        setContent {
            AlarmyTheme {

            }
        }
    }
}
