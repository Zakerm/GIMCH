package com.diplom.gimch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginByPhoneNumber : AppCompatActivity() {

    private lateinit var editPhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_by_phone_number)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editPhone = findViewById(R.id.loginPhone)

        // Устанавливаем +7 при запуске
        editPhone.setText("+7")
        editPhone.setSelection(editPhone.text.length)

        // Защищаем +7 и ограничиваем длину
        editPhone.addTextChangedListener(object : TextWatcher {
            var isEditing = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isEditing) return
                isEditing = true

                val input = s.toString()

                // Удалили +7 или заменили — сбросим
                if (!input.startsWith("+7")) {
                    editPhone.setText("+7")
                    editPhone.setSelection(editPhone.text.length)
                } else {
                    // Удаляем всё кроме цифр после +7
                    val digits = input.replace("+7", "").replace("\\D".toRegex(), "")
                    val limited = digits.take(10)
                    val result = "+7$limited"
                    editPhone.setText(result)
                    editPhone.setSelection(result.length)
                }

                isEditing = false
            }
        })
    }
}
