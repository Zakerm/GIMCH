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

        // Изначально кнопка "Вперёд" неактивна и окрашена в серый (#6D6767)
        buttonRight.isEnabled = false
        buttonRight.imageTintList = ColorStateList.valueOf(Color.parseColor("#6D6767"))

        // Слушатель изменения текста с проверкой введённого имени
        editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString().trim()
                val errorMessage = when {
                    input.isEmpty() -> null // Не показываем ошибку, если поле пустое
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
            val intent = Intent(this, DateOfBirthActivity::class.java)
            startActivity(intent)
        }
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
