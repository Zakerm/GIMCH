package com.diplom.gimch

import android.content.Context
import android.content.Intent
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
import java.util.*

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

        val editDay = findViewById<EditText>(R.id.editTextDay)
        val editMonth = findViewById<EditText>(R.id.editTextMonth)
        val editYear = findViewById<EditText>(R.id.editTextYear)
        val textError = findViewById<TextView>(R.id.textErrorDateOfBirth)
        val buttonRight = findViewById<ImageButton>(R.id.buttonDateOfBirthRight)
        val buttonLeft = findViewById<ImageButton>(R.id.buttonDateOfBirthLeft)

        val prefs = getSharedPreferences("user_input", Context.MODE_PRIVATE)

        // Восстановление сохранённых данных
        editDay.setText(prefs.getString("dob_day", ""))
        editMonth.setText(prefs.getString("dob_month", ""))
        editYear.setText(prefs.getString("dob_year", ""))

        // Проверка даты
        fun validateDate() {
            val day = editDay.text.toString().toIntOrNull()
            val month = editMonth.text.toString().toIntOrNull()
            val year = editYear.text.toString().toIntOrNull()

            val calendarNow = Calendar.getInstance()
            val currentYear = 2025
            val currentMonth = calendarNow.get(Calendar.MONTH) + 1
            val currentDay = calendarNow.get(Calendar.DAY_OF_MONTH)

            val errorMessage = when {
                day == null || month == null || year == null ->
                    "Пожалуйста, заполните все поля."

                year > currentYear || year < 1900 ->
                    "Проверь год рождения."

                month !in 1..12 ->
                    "Месяц должен быть от 1 до 12."

                day !in 1..31 ->
                    "День должен быть от 1 до 31."

                (month == 2 && day > 29) ||
                        (month in listOf(4, 6, 9, 11) && day > 30) ->
                    "Проверь корректность дня в месяце."

                (currentYear - year < 16) ||
                        (currentYear - year == 16 && (month > currentMonth || (month == currentMonth && day > currentDay))) ->
                    "Регистрация доступна только с 16 лет."

                else -> null
            }

            val isValid = errorMessage == null

            buttonRight.isEnabled = isValid
            buttonRight.setColorFilter(
                if (isValid) Color.parseColor("#FF6F61") else Color.parseColor("#6D6767")
            )

            if (!isValid) {
                textError.text = errorMessage
                textError.visibility = View.VISIBLE
            } else {
                textError.visibility = View.GONE
            }
        }

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateDate()
            }
        }

        editDay.addTextChangedListener(textWatcher)
        editMonth.addTextChangedListener(textWatcher)
        editYear.addTextChangedListener(textWatcher)

        // Установка начального состояния кнопки
        validateDate()

        // Кнопка "Назад"
        buttonLeft.setOnClickListener {
            startActivity(Intent(this, SayMyNameActivity::class.java))
        }

        // Кнопка "Вперёд"
        buttonRight.setOnClickListener {
            // Сохраняем дату рождения в SharedPreferences
            prefs.edit()
                .putString("dob_day", editDay.text.toString())
                .putString("dob_month", editMonth.text.toString())
                .putString("dob_year", editYear.text.toString())
                .apply()

            startActivity(Intent(this, PhoneNumberRegistrationActivity::class.java))
        }
    }

    // Скрытие клавиатуры при тапе вне EditText
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}
