package com.example.customseekbar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customseekbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Todo Bir çizgi ve daire var seekbar'da,Çizgi farklı bir renk. Geldiğim yere kadar ki kısım mavi olsun,daha sonraki kısım gri kalsın
        //Todo Bunun için ilk Kalıtım alıp değiştirilmesi gereken yerleri değiştir diyebiliyorsunuz.. Hepsini üst sınıfı olan view sınıfından kalıtım alıcaz
        //Todo
        val custom = CustomSeekBar(this)
    }
}