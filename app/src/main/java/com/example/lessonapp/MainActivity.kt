package com.example.lessonapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lessonapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // back button
        binding.backBtn.setOnClickListener {
             onBackPressed()
        }

        // share app button
        binding.share.setOnClickListener {
            shareAppLink()
        }

        // close app button
        binding.finish.setOnClickListener {
            onBackPressed()
        }

    }
    private fun shareAppLink() {
        val appLink = "https://play.google.com/store/apps/details?id=com.yourapp.package"
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this app: $appLink")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share app via"))
    }
}