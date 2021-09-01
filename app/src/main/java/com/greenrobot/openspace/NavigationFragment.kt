package com.greenrobot.openspace

import android.R
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.ar.core.exceptions.CameraNotAvailableException
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.Sceneform
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment


class NavigationFragment: Fragment()  {

    var sceneView:SceneView?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        var view:View = inflater.inflate(
            com.greenrobot.openspace.R.layout.navigation_fragment,
            container,
            false
        )
        sceneView = view.findViewById(com.greenrobot.openspace.R.id.sceneView)
        return view
    }

    override fun onResume() {
        super.onResume()
        try {
            renderLocalObject()
            sceneView?.resume()
        } catch (e: CameraNotAvailableException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            sceneView?.destroy()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addNodeToScene(model: ViewRenderable) {
        if (sceneView != null) {

        }
    }

    private fun renderLocalObject() {
        val myView = ViewRenderable.builder()
            .setView(activity, com.greenrobot.openspace.R.layout.navigation_fragment)
            .build()
        myView.thenAccept {
            addNodeToScene(it)
        }

        var localModel = "donkeya.sfb"

        ModelRenderable.builder()
            .setSource(activity, Uri.parse(localModel))
            .setRegistryId(localModel)
            .build()
            .thenAccept { modelRenderable: ModelRenderable ->
                val node = Node()
                node.renderable = modelRenderable
            }


    }

}



