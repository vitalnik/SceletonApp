package com.example.featuremodule2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.featuremodule2.ui.home.Feature2HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Feature2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature2)
    }
}