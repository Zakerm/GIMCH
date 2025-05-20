package com.diplom.gimch

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class UploadingPhoto : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var buttonSelect: Button

    // Обработчик результата выбора фото
    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageView.setImageURI(it) // Показываем фото в ImageView
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploading_photo)

        imageView = findViewById(R.id.imageViewPhoto)
        buttonSelect = findViewById(R.id.buttonSelectPhoto)

        buttonSelect.setOnClickListener {
            // Запуск выбора изображения
            getImage.launch("image/*")
        }
    }
}
