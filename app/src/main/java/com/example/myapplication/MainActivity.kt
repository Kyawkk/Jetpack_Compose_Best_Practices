@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.screen.ReplyApp
import com.example.myapplication.ui.theme.ReplyTheme

@ExperimentalMaterial3WindowSizeClassApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReplyTheme {
                Surface {
                    val windowSize = calculateWindowSizeClass(this)
                    ReplyApp(windowSize = windowSize.widthSizeClass)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ReplyAppPreview() {
    Surface {
        ReplyApp(windowSize = WindowWidthSizeClass.Expanded)
    }
}