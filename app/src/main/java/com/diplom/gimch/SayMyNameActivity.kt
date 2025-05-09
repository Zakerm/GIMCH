package com.diplom.gimch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SayMyNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_say_my_name)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Кнопка для перемещения в активность пола человека
        val buttonLeft = findViewById<ImageButton>(R.id.ButtonSayMyNameLeft)
        buttonLeft.setOnClickListener {
            val intent = Intent(this, GenderActivity::class.java)
            startActivity(intent)
        }

        // Кнопка для перемещения в активность дня рождения человека
        val buttonRight = findViewById<ImageButton>(R.id.ButtonSayMyNameRight)
        buttonRight.setOnClickListener {
            val intent = Intent(this, DateOfBirthActivity::class.java)
            startActivity(intent)
        }
    }
}