package com.diplom.gimch

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PhoneNumberRegistrationActivity : AppCompatActivity() {

    private lateinit var editPhone: EditText
    private lateinit var errorText: TextView
    private lateinit var buttonRight: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_phone_number_registration)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editPhone = findViewById(R.id.editTextPhoneNumber)
        errorText = findViewById(R.id.textPhoneNumberError)
        val buttonLeft = findViewById<ImageButton>(R.id.buttonPhoneNumberRegistrationLeft)
        buttonRight = findViewById(R.id.buttonPhoneNumberRegistrationRight)

        // Установка +7 в начало
        editPhone.setText("+7")
        editPhone.setSelection(editPhone.text.length)

        // Обработка ввода номера
        editPhone.addTextChangedListener(object : TextWatcher {
            var isEditing = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isEditing) return
                isEditing = true

                val input = s.toString()
                if (!input.startsWith("+7")) {
                    editPhone.setText("+7")
                    editPhone.setSelection(editPhone.text.length)
                } else {
                    val onlyDigits = input.replace("+7", "").replace("\\D".toRegex(), "")
                    val limitedDigits = onlyDigits.take(10)
                    editPhone.setText("+7$limitedDigits")
                    editPhone.setSelection(editPhone.text.length)
                }

                validatePhone()
                isEditing = false
            }
        })

        // Кнопка назад
        buttonLeft.setOnClickListener {
            val intent = Intent(this, DateOfBirthActivity::class.java)
            startActivity(intent)
        }

        // Кнопка вперёд
        //buttonRight.setOnClickListener {
          //  val intent = Intent(this, NextActivity::class.java) // замените на нужную активность
          //  startActivity(intent)
        //}

        // Начальное состояние кнопки
        buttonRight.isEnabled = false
        buttonRight.setColorFilter(Color.parseColor("#6D6767"))
    }

    private fun validatePhone() {
        val input = editPhone.text.toString()
        val onlyDigits = input.replace("+7", "")

        val error = when {
            onlyDigits.isEmpty() -> "Введите номер телефона."
            onlyDigits.length < 10 -> "Номер должен содержать 10 цифр после +7."
            else -> null
        }

        val isValid = error == null
        buttonRight.isEnabled = isValid
        buttonRight.setColorFilter(if (isValid) Color.BLACK else Color.parseColor("#6D6767"))

        if (isValid) {
            errorText.visibility = TextView.GONE
        } else {
            errorText.text = error
            errorText.visibility = TextView.VISIBLE
        }
    }

    // Скрытие клавиатуры при касании вне EditText
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}
