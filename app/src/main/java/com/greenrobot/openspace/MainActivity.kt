package com.greenrobot.openspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.filament.Filament
import com.greenrobot.openspace.ui.theme.OpenSpaceTheme
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Filament.init()
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        setContent {
            HelloWorldApp()
        }
    }
}

@Composable
fun HelloWorldApp() {
    // Background Image
    val backgroundImage = painterResource(id = R.drawable.universe_background)
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val environmentLoader = rememberEnvironmentLoader(engine)

    // Content
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = backgroundImage,
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Adjust content scale as needed
        )
        Scene(
            modifier = Modifier.fillMaxSize(),
            engine = engine,
            modelLoader = modelLoader,
            isOpaque = false,
            childNodes = rememberNodes {
                add(ModelNode(modelLoader.createModelInstance("apple.glb")).apply {
                    // Position the first apple
                    position = Position(0f, 0f, 0f)
                })
                add(ModelNode(modelLoader.createModelInstance("apple2.glb")).apply {
                    // Position the second apple
                    position = Position(2f, 0f, 0f)
                })
            },
            //environment = environmentLoader.createHDREnvironment("environment.hdr")!!
        )
    }
}