package com.diplom.gimch

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SayMyNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_say_my_name)

        // Применяем отступы согласно системным insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Найдём элементы интерфейса
        val editTextName = findViewById<EditText>(R.id.editTextSayMyName)
        val textError = findViewById<TextView>(R.id.textError)
        val buttonRight = findViewById<ImageButton>(R.id.ButtonSayMyNameRight)
        val buttonLeft = findViewById<ImageButton>(R.id.ButtonSayMyNameLeft)

        // Восстановление сохранённого имени
        val prefs = getSharedPreferences("user_input", Context.MODE_PRIVATE)
        val savedName = prefs.getString("name", "")
        editTextName.setText(savedName)

        // Изначально кнопка "Вперёд" неактивна и окрашена в серый, если имя некорректное
        val initialInput = savedName?.trim() ?: ""
        val initialIsValid = isValidName(initialInput)
        buttonRight.isEnabled = initialIsValid
        buttonRight.imageTintList = ColorStateList.valueOf(
            if (initialIsValid) Color.parseColor("#FF6F61") else Color.parseColor("#6D6767")
        )

        // Слушатель изменения текста с проверкой введённого имени
        editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString().trim()
                val errorMessage = when {
                    input.isEmpty() -> null
                    input.length < 2 -> "Имя слишком короткое."
                    input.length > 10 -> "Имя не должно быть длиннее 10 символов."
                    !input[0].toString().matches(Regex("[А-ЯЁ]")) ->
                        "Имя должно начинаться с заглавной буквы."
                    !input.matches(Regex("^[А-ЯЁа-яё]+$")) ->
                        "Имя должно содержать только русские буквы."
                    else -> null
                }

                val isValid = errorMessage == null

                // Меняем активность и цвет кнопки
                buttonRight.isEnabled = isValid
                buttonRight.imageTintList = ColorStateList.valueOf(
                    if (isValid) Color.parseColor("#FF6F61") else Color.parseColor("#6D6767")
                )

                // Показываем или скрываем сообщение об ошибке
                if (errorMessage != null) {
                    textError.text = errorMessage
                    textError.visibility = View.VISIBLE
                } else {
                    textError.visibility = View.GONE
                }
            }
        })

        // Кнопка "Назад"
        buttonLeft.setOnClickListener {
            val intent = Intent(this, GenderActivity::class.java)
            startActivity(intent)
        }

        // Кнопка "Вперёд"
        buttonRight.setOnClickListener {
            val name = editTextName.text.toString()
            prefs.edit().putString("name", name).apply()

            val intent = Intent(this, DateOfBirthActivity::class.java)
            startActivity(intent)
        }
    }

    // Проверка имени (используется дважды, вынесено в отдельную функцию)
    private fun isValidName(name: String): Boolean {
        return name.length in 2..10 &&
                name[0].toString().matches(Regex("[А-ЯЁ]")) &&
                name.matches(Regex("^[А-ЯЁа-яё]+$"))
    }

    // Скрытие клавиатуры при нажатии вне EditText
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}
