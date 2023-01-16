package com.example.petmaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petmaps.databinding.ActivityMainBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("ee50f510-d916-4066-95e3-047977bcd76b");
        MapKitFactory.initialize(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapView = binding.mapview
        mapView.map.move(
            CameraPosition(
                Point(
                    55.751574, 37.573856
                ),
                11.0f,
                0.0f,
                0.0f
            ),
            Animation(Animation.Type.SMOOTH, 0.0f),
            null
        )

    }
}