package com.example.translate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customseekbar.databinding.ActivityTranslateAndScaleBinding

class TranslateAndScaleActivity : AppCompatActivity() {

    lateinit var binding: ActivityTranslateAndScaleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslateAndScaleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}