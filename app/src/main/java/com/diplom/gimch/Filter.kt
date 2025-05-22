package com.diplom.gimch
import androidx.core.content.res.ResourcesCompat
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Filter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_filter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinnerCity = findViewById<Spinner>(R.id.spinnerCity)
        val spinnerPurpose = findViewById<Spinner>(R.id.spinnerPurpose)
        val spinnerAge = findViewById<Spinner>(R.id.spinnerAge)

        val cities = listOf("Москва", "Санкт-Петербург", "Йошкар-Ола", "Казань")
        val purposes = listOf("Поиск отношений", "Игры", "Общение")
        val ages = listOf("16-18","18-25", "26-35", "36-45", "46+", "Неважно")

        // Загружаем шрифт из ресурсов
        val juraBold = ResourcesCompat.getFont(this, R.font.jura)!!

        // Кастомный адаптер с шрифтом
        val cityAdapter = CustomFontSpinnerAdapter(this, cities, juraBold)
        val purposeAdapter = CustomFontSpinnerAdapter(this, purposes, juraBold)
        val ageAdapter = CustomFontSpinnerAdapter(this, ages, juraBold)

        spinnerCity.adapter = cityAdapter
        spinnerPurpose.adapter = purposeAdapter
        spinnerAge.adapter = ageAdapter
    }
}

// Кастомный адаптер для применения шрифта
class CustomFontSpinnerAdapter(
    context: android.content.Context,
    private val items: List<String>,
    private val font: Typeface
) : ArrayAdapter<String>(context, R.layout.spinner_item, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.typeface = font
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.typeface = font
        return view
    }
}
