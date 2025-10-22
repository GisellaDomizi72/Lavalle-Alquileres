package com.giselladomizi.first.ui.main

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.giselladomizi.first.R
import com.giselladomizi.first.data.local.databese.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditarAlquilerActivity : AppCompatActivity() {

    private lateinit var editTipo: EditText
    private lateinit var editUbicacion: EditText
    private lateinit var editDescripcion: EditText
    private lateinit var editImagen: ImageView
    private lateinit var btnCambiarImagen: Button
    private lateinit var btnGuardarCambios: Button

    private var alquilerId: Int = 0
    private var imagenUri: String = "" // ruta de la imagen actual

    // ActivityResultLauncher para seleccionar imagen de galería
    private val seleccionarImagenLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imagenUri = it.toString() // guardamos la URI como String
            Glide.with(this)
                .load(imagenUri)
                .placeholder(android.R.color.darker_gray)
                .into(editImagen)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ActivityCollector.addActivity(this)
        setContentView(R.layout.activity_editar_alquiler)

        // Inicializar vistas
        editTipo = findViewById(R.id.editTipo)
        editUbicacion = findViewById(R.id.editUbicacion)
        editDescripcion = findViewById(R.id.editDescripcion)
        editImagen = findViewById(R.id.editImagen)
        btnCambiarImagen = findViewById(R.id.btnCambiarImagen)
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios)

        // Recuperar id de alquiler
        alquilerId = intent.getIntExtra("id_alqui", 0)
        if (alquilerId == 0) {
            Toast.makeText(this, "No se pudo cargar la publicación", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        val db = AppDatabase.getDatabase(applicationContext)

        // Cargar datos del alquiler
        lifecycleScope.launch {
            val alquiler = db.alquilerDAO().getAlquilerById(alquilerId)
            alquiler?.let {
                editTipo.setText(it.tipo_alqui)
                editUbicacion.setText(it.ubicacion_alqui)
                editDescripcion.setText(it.descripcion_alqui)
                imagenUri = it.imagen_alqui

                if (it.imagen_alqui.isNotEmpty()) {
                    Glide.with(editImagen.context)
                        .load(it.imagen_alqui)
                        .placeholder(android.R.color.darker_gray)
                        .into(editImagen)
                }
            }
        }
        // Abrir galería para cambiar imagen
        btnCambiarImagen.setOnClickListener {
            seleccionarImagenLauncher.launch("image/*")
        }

        // Guardar cambios
        btnGuardarCambios.setOnClickListener {
            lifecycleScope.launch {
                val alquiler = db.alquilerDAO().getAlquilerById(alquilerId)
                alquiler?.let {
                    val updatedAlquiler = it.copy(
                        tipo_alqui = editTipo.text.toString(),
                        ubicacion_alqui = editUbicacion.text.toString(),
                        descripcion_alqui = editDescripcion.text.toString(),
                        imagen_alqui = imagenUri
                    )
                    db.alquilerDAO().updateAlquiler(updatedAlquiler)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@EditarAlquilerActivity, "Cambios guardados", Toast.LENGTH_SHORT).show()
                        finish() // vuelve a MispublicacionesActivity
                    }
                }
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}