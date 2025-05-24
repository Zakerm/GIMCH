package com.diplom.gimch

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.imageview.ShapeableImageView

class Profile : AppCompatActivity() {

    private lateinit var buttonEditProfile: Button
    private lateinit var buttonSaveProfile: Button
    private lateinit var buttonCancelProfile: Button

    // Первый набор фото и кнопок
    private lateinit var bgEditPhotoProf1: View
    private lateinit var penEditPhotoProf1: ImageButton
    private lateinit var deleteEditPhotoProf1: ImageButton

    // Второй набор фото и кнопок
    private lateinit var bgEditPhotoProf2: View
    private lateinit var penEditPhotoProf2: ImageButton
    private lateinit var deleteEditPhotoProf2: ImageButton
    private lateinit var addImgProf2: ImageButton

    // Третий набор фото и кнопок
    private lateinit var bgEditPhotoProf3: View
    private lateinit var penEditPhotoProf3: ImageButton
    private lateinit var deleteEditPhotoProf3: ImageButton
    private lateinit var addImgProf3: ImageButton

    private lateinit var shapeableImageView1: ShapeableImageView
    private lateinit var shapeableImageView2: ShapeableImageView
    private lateinit var shapeableImageView3: ShapeableImageView

    private lateinit var profileText: EditText
    private lateinit var textCount: TextView

    private var photoUri1: Uri? = null
    private var photoUri2: Uri? = null
    private var photoUri3: Uri? = null

    private var originalPhotoUri1: Uri? = null
    private var originalPhotoUri2: Uri? = null
    private var originalPhotoUri3: Uri? = null

    private var originalProfileText: String = ""

    private var isEditing = false

    private val pickImageLauncher1 = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            photoUri1 = it
            shapeableImageView1.setImageURI(it)
            updateUI()
        }
    }

    private val pickImageLauncher2 = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            photoUri2 = it
            shapeableImageView2.setImageURI(it)
            updateUI()
        }
    }

    private val pickImageLauncher3 = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            photoUri3 = it
            shapeableImageView3.setImageURI(it)
            updateUI()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonEditProfile = findViewById(R.id.buttonEditProfile)
        buttonSaveProfile = findViewById(R.id.buttonSaveProfile)
        buttonCancelProfile = findViewById(R.id.buttonCancelProfile)

        // 1
        bgEditPhotoProf1 = findViewById(R.id.BGEditPhotoProf1)
        penEditPhotoProf1 = findViewById(R.id.PenEditPhotoProf1)
        deleteEditPhotoProf1 = findViewById(R.id.DeleteEditPhotoProf1)

        // 2
        bgEditPhotoProf2 = findViewById(R.id.BGEditPhotoProf2)
        penEditPhotoProf2 = findViewById(R.id.PenEditPhotoProf2)
        deleteEditPhotoProf2 = findViewById(R.id.DeleteEditPhotoProf2)
        addImgProf2 = findViewById(R.id.addImgProf2)

        // 3
        bgEditPhotoProf3 = findViewById(R.id.BGEditPhotoProf3)
        penEditPhotoProf3 = findViewById(R.id.PenEditPhotoProf3)
        deleteEditPhotoProf3 = findViewById(R.id.DeleteEditPhotoProf3)
        addImgProf3 = findViewById(R.id.addImgProf3)

        shapeableImageView1 = findViewById(R.id.shapeableImageView1)
        shapeableImageView2 = findViewById(R.id.shapeableImageView2)
        shapeableImageView3 = findViewById(R.id.shapeableImageView3)

        profileText = findViewById(R.id.ProfileText)
        textCount = findViewById(R.id.textCount)

        photoUri1 = null
        photoUri2 = null
        photoUri3 = null

        originalPhotoUri1 = photoUri1
        originalPhotoUri2 = photoUri2
        originalPhotoUri3 = photoUri3

        originalProfileText = profileText.text.toString()

        profileText.isEnabled = false
        textCount.visibility = View.GONE

        profileText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val length = s?.length ?: 0
                textCount.text = "$length/100"
                if (length > 100) {
                    profileText.error = "Максимум 100 символов"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        buttonEditProfile.setOnClickListener {
            enterEditMode()
        }

        buttonSaveProfile.setOnClickListener {
            val length = profileText.text.length
            if (length > 100) {
                Toast.makeText(this, "Максимум 100 символов!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (photoUri1 == null) {
                Toast.makeText(this, "Сначала загрузите первую фотографию", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            saveChanges()
        }

        buttonCancelProfile.setOnClickListener {
            cancelChanges()
        }

        // Фото 1
        penEditPhotoProf1.setOnClickListener {
            pickImageLauncher1.launch("image/*")
        }
        deleteEditPhotoProf1.setOnClickListener {
            if (photoUri1 != null) {
                photoUri1 = null
                shapeableImageView1.setImageDrawable(null)
                updateUI()
            }
        }

        // Фото 2
        penEditPhotoProf2.setOnClickListener {
            pickImageLauncher2.launch("image/*")
        }
        deleteEditPhotoProf2.setOnClickListener {
            if (photoUri2 != null) {
                photoUri2 = null
                shapeableImageView2.setImageDrawable(null)
                updateUI()
            }
        }
        addImgProf2.setOnClickListener {
            enterEditMode()
            pickImageLauncher2.launch("image/*")
        }

        // Фото 3
        penEditPhotoProf3.setOnClickListener {
            pickImageLauncher3.launch("image/*")
        }
        deleteEditPhotoProf3.setOnClickListener {
            if (photoUri3 != null) {
                photoUri3 = null
                shapeableImageView3.setImageDrawable(null)
                updateUI()
            }
        }
        addImgProf3.setOnClickListener {
            enterEditMode()
            pickImageLauncher3.launch("image/*")
        }

        updateUI()
    }

    private fun enterEditMode() {
        isEditing = true
        originalPhotoUri1 = photoUri1
        originalPhotoUri2 = photoUri2
        originalPhotoUri3 = photoUri3
        originalProfileText = profileText.text.toString()

        profileText.isEnabled = true
        textCount.visibility = View.VISIBLE

        updateUI()
    }

    private fun saveChanges() {
        isEditing = false

        originalPhotoUri1 = photoUri1
        originalPhotoUri2 = photoUri2
        originalPhotoUri3 = photoUri3
        originalProfileText = profileText.text.toString()

        profileText.isEnabled = false
        textCount.visibility = View.GONE

        updateUI()
        Toast.makeText(this, "Профиль сохранён", Toast.LENGTH_SHORT).show()
    }

    private fun cancelChanges() {
        isEditing = false

        photoUri1 = originalPhotoUri1
        photoUri2 = originalPhotoUri2
        photoUri3 = originalPhotoUri3
        profileText.setText(originalProfileText)

        if (photoUri1 != null) {
            shapeableImageView1.setImageURI(photoUri1)
        } else {
            shapeableImageView1.setImageDrawable(null)
        }

        if (photoUri2 != null) {
            shapeableImageView2.setImageURI(photoUri2)
        } else {
            shapeableImageView2.setImageDrawable(null)
        }

        if (photoUri3 != null) {
            shapeableImageView3.setImageURI(photoUri3)
        } else {
            shapeableImageView3.setImageDrawable(null)
        }

        profileText.isEnabled = false
        textCount.visibility = View.GONE

        updateUI()
    }

    private fun updateUI() {
        val photo1Exists = photoUri1 != null
        val photo2Exists = photoUri2 != null
        val photo3Exists = photoUri3 != null

        if (isEditing) {
            buttonSaveProfile.visibility = View.VISIBLE
            buttonCancelProfile.visibility = View.VISIBLE
            buttonEditProfile.visibility = View.GONE

            // 1-й набор
            bgEditPhotoProf1.visibility = View.VISIBLE
            penEditPhotoProf1.visibility = View.VISIBLE
            deleteEditPhotoProf1.visibility = View.VISIBLE

            // 2-й набор
            bgEditPhotoProf2.visibility = View.VISIBLE
            penEditPhotoProf2.visibility = View.VISIBLE
            deleteEditPhotoProf2.visibility = View.VISIBLE
            addImgProf2.visibility = if (photo2Exists) View.GONE else View.VISIBLE

            // 3-й набор
            bgEditPhotoProf3.visibility = View.VISIBLE
            penEditPhotoProf3.visibility = View.VISIBLE
            deleteEditPhotoProf3.visibility = View.VISIBLE
            addImgProf3.visibility = if (photo3Exists) View.GONE else View.VISIBLE

            profileText.isEnabled = true
            textCount.visibility = View.VISIBLE

        } else {
            buttonSaveProfile.visibility = View.GONE
            buttonCancelProfile.visibility = View.GONE
            buttonEditProfile.visibility = View.VISIBLE

            bgEditPhotoProf1.visibility = View.GONE
            penEditPhotoProf1.visibility = View.GONE
            deleteEditPhotoProf1.visibility = View.GONE

            bgEditPhotoProf2.visibility = View.GONE
            penEditPhotoProf2.visibility = View.GONE
            deleteEditPhotoProf2.visibility = View.GONE
            addImgProf2.visibility = if (photo2Exists) View.GONE else View.VISIBLE

            bgEditPhotoProf3.visibility = View.GONE
            penEditPhotoProf3.visibility = View.GONE
            deleteEditPhotoProf3.visibility = View.GONE
            addImgProf3.visibility = if (photo3Exists) View.GONE else View.VISIBLE

            profileText.isEnabled = false
            textCount.visibility = View.GONE
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val scrcoords = IntArray(2)
                v.getLocationOnScreen(scrcoords)
                val x = ev.rawX.toInt()
                val y = ev.rawY.toInt()

                if (x < scrcoords[0] || x > scrcoords[0] + v.width ||
                    y < scrcoords[1] || y > scrcoords[1] + v.height) {
                    // Клик вне EditText — скрываем клавиатуру и снимаем фокус
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    v.clearFocus()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}
