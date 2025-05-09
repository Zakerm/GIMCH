package com.diplom.gimch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gender)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Поиск кнопки и установка обработчика если выбран мужской пол
        val buttonMan = findViewById<Button>(R.id.buttonMan)
        buttonMan.setOnClickListener {
            val intent = Intent(this, SayMyNameActivity::class.java)
            startActivity(intent)
        }

        // Поиск кнопки и установка обработчика если выбран женский пол
        val buttonGirl = findViewById<Button>(R.id.buttonGirl)
        buttonGirl.setOnClickListener {
            val intent = Intent(this, SayMyNameActivity::class.java)
            startActivity(intent)
        }
    }
}