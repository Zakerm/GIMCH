package com.diplom.gimch

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DateOfBirthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_date_of_birth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Кнопка для перемещения в активность пола человека
        val buttonLeft = findViewById<ImageButton>(R.id.buttonDateOfBirthLeft)
        buttonLeft.setOnClickListener {
            val intent = Intent(this, SayMyNameActivity::class.java)
            startActivity(intent)
        }

        // Кнопка для перемещения в активность регистрации номера телефона
        val buttonRight = findViewById<ImageButton>(R.id.buttonDateOfBirthRight)
        buttonRight.setOnClickListener {
            val intent = Intent(this, PhoneNumberRegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}