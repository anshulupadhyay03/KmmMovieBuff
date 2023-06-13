package com.retroent.moviebuff

import App
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun mainView() = CompositionLocalProvider(){
    MaterialTheme {
        App()
    }
}