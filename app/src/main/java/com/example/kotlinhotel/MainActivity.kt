package com.example.kotlinhotel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinhotel.databinding.ActivityMainBinding
import io.github.serpro69.kfaker.faker

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val faker = faker { }
        binding.greetingText.text = "Hello ${faker.name.firstName()}!"
    }
}