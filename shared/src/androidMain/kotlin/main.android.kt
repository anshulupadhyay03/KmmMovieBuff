package com.retroent.moviebuff

import App
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import decompose.MovieBuffRoot

@Composable
fun mainView(root: MovieBuffRoot) = CompositionLocalProvider(){
    MaterialTheme {
        App(root)
    }
}