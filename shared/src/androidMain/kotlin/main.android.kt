package com.retroent.moviebuff

import App
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import decompose.MovieBuffRoot

@Composable
fun mainView(root: MovieBuffRoot) = CompositionLocalProvider() {
    App(root)
}