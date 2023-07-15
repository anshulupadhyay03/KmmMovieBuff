package com.retroent.moviebuff.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.retroent.moviebuff.mainView
import decompose.MovieBuffRootImpl

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = MovieBuffRootImpl(defaultComponentContext())
        setContent {
            mainView(root)
        }
    }
}

