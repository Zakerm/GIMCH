package com.diplom.gimch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Главные кнопки
        val buttonLogin = findViewById<Button>(R.id.buttonMainLogin)
        val buttonCreateProfile = findViewById<Button>(R.id.buttonCreateProfile)

        // Кнопки соц. входа
        val buttonApple = findViewById<Button>(R.id.buttonMainApple)
        val buttonGoogle = findViewById<Button>(R.id.buttonMainGoogle)
        val buttonTelegram = findViewById<Button>(R.id.buttonMainTelegram)
        val buttonPhone = findViewById<Button>(R.id.buttonMainNumberPhone)
        val buttonComeBack = findViewById<Button>(R.id.buttonMainComeBack)

        // Иконки
        val imageApple = findViewById<ImageView>(R.id.imageViewMainApple)
        val imageGoogle = findViewById<ImageView>(R.id.imageViewMainGoogle)
        val imageTelegram = findViewById<ImageView>(R.id.imageViewMainTelegram)
        val imagePhone = findViewById<ImageView>(R.id.imageViewMainPhone)

        // Группа элементов входа
        val loginGroup = listOf<View>(
            buttonApple, buttonGoogle, buttonTelegram, buttonPhone,
            buttonComeBack, imageApple, imageGoogle, imageTelegram, imagePhone
        )

        // Изначально скрыть элементы входа
        loginGroup.forEach { it.visibility = View.GONE }

        // При нажатии "Войти" — показать вход, скрыть главные кнопки
        buttonLogin.setOnClickListener {
            buttonLogin.visibility = View.GONE
            buttonCreateProfile.visibility = View.GONE
            loginGroup.forEach { it.visibility = View.VISIBLE }
        }

        // При нажатии "Назад" — скрыть вход, показать главные кнопки
        buttonComeBack.setOnClickListener {
            buttonLogin.visibility = View.VISIBLE
            buttonCreateProfile.visibility = View.VISIBLE
            loginGroup.forEach { it.visibility = View.GONE }
        }

        // При нажатии "Создать профиль" — переход к следующей активности
        buttonCreateProfile.setOnClickListener {
            val intent = Intent(this, GenderActivity::class.java)
            startActivity(intent)
        }
    }
}
