package com.greenrobot.openspace

import android.content.Intent
import android.os.Bundle
import android.transition.Scene
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.google.android.filament.Filament
import com.google.firebase.auth.FirebaseAuth
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.firebase.ui.auth.AuthUI
import com.google.android.filament.Engine
import com.google.android.filament.EntityInstance
import io.github.sceneview.Entity
import io.github.sceneview.SceneView
import io.github.sceneview.components.CameraComponent
import io.github.sceneview.math.Scale
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberView

class MainActivity : ComponentActivity() {
    private val TAG: String? = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Filament.init()
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)


        // Check if user is signed in (non-null) and update UI accordingly.
        // Check if user is signed in (non-null) and update UI accordingly.
        val mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        if (currentUser != null) {
            Log.e(TAG,"signed in")
            // User is signed in, do whatever you need to do when the user is logged in.
            // For example, you can get the user's information using currentUser.getEmail(), currentUser.getUid(), etc.
        } else {
            Log.e(TAG,"signed out")
            val intent: Intent? = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // User is signed out, navigate the user to the login screen or prompt them to log in.
        }


        setContent {
            TabScreen(this)
        }

    }


}


@Composable
fun Apples() {
    DisposableEffect(Unit) {
        // Code to run on composition start
        println("Composable started")

        onDispose {
            // Code to run on composition end or recomposition
            println("Composable disposed")
            // Perform cleanup operations here
        }
    }

    val engine = rememberEngine()
    // Background Image
    val backgroundImage = painterResource(id = R.drawable.universe_background)
    val modelLoader = rememberModelLoader(engine)
//    val cameraNode = rememberCameraNode(engine) {
//        // Content
//
//        position = Position(z = 4.0f)
//    }
    //val cameraNode = SceneView.DefaultCameraNode(engine)
    val cameraNode = SceneView.createCameraNode(engine)
    //cameraNode = rememberCameraNode(engine).apply {
    //    position = Position(z = -4.0f)
    //} as SceneView.DefaultCameraNode
    //cameraNode.position  = Position(z = 100.0f)


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
            cameraNode = cameraNode,
            activity = LocalContext.current as? ComponentActivity,
            lifecycle = LocalLifecycleOwner.current.lifecycle,
            view = rememberView(engine),
            childNodes = rememberNodes {
                add(ModelNode(modelLoader.createModelInstance("earth.glb")).apply {
                    // Position the first apple
                    position = Position(-5f, 0f, -30f)
                    scale = Scale(10f, 10f, 10f)
                })
                add(ModelNode(modelLoader.createModelInstance("spaceshipc.glb")).apply {
                    // Position the second apple
                    position = Position(5f, 0f, -30f)
                })
            },
            //environment = environmentLoader.createHDREnvironment("environment.hdr")!!
        )
    }


}



@Composable
fun TabScreen(activity: ComponentActivity) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex,
            modifier = Modifier.height(56.dp) // Set the height of the TabRow here
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 }
            ) {
                Text("ViewPort")
            }
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 }
            ) {
                Text("Account")
            }
            Tab(
                selected = selectedTabIndex == 2,
                onClick = { selectedTabIndex = 2 }
            ) {
                Text("About")
            }
        }

        when (selectedTabIndex) {

            0 -> Apples()
            1 -> AccountScreen(activity = activity)
            2 -> TabContent("Content for Tab 3")
        }
    }
}
fun signOut(activity: ComponentActivity) {
    AuthUI.getInstance()
        .signOut(activity)
        .addOnCompleteListener {
            // ...
            Log.e("MAINACTIVITY", "signed out")
        }

    val intent = Intent(activity, LoginActivity::class.java)
    activity.startActivity(intent)
    activity.finish() // Finish the current activity (optional)
}
@Composable
fun AccountScreen(activity: ComponentActivity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Your Account", color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { signOut(activity) }) {
            Text(text = "Sign Out")
        }
    }
}

@Composable
fun TabContent(content: String) {
    Text(
        text = content,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}

