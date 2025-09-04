package com.giselladomizi.first.ui.main

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.giselladomizi.first.R
import com.giselladomizi.first.data.local.databese.AppDatabase
import com.giselladomizi.first.data.local.entity.Alquiler
import kotlinx.coroutines.launch
import android.app.Activity
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class AddpublicacionActivity : AppCompatActivity() {

    private var imagenUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addpublicacion)

        val btnInicio: ImageButton = findViewById(R.id.btnInicio)
        val btnVerMiPerfil: ImageButton = findViewById(R.id.btnVerMiPerfil)
        val btnMisPublicabiones: ImageButton = findViewById(R.id.btnMisPublicabiones)

        val tipoAlqui = findViewById<EditText>(R.id.tipo_alqui)
        val ubicacionAlqui = findViewById<EditText>(R.id.ubicaion_alqui)
        val descripcionAlqui = findViewById<EditText>(R.id.descripcion_alqui)
        val imagenAlqui = findViewById<ImageView>(R.id.imagen_alqui)

        val btnSeleccionarImagen = findViewById<Button>(R.id.id_button)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarPublicacion)

        // 👉 Seleccionar imagen
        val pickImage =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    imagenUri = result.data?.data
                    imagenAlqui.setImageURI(imagenUri)
                }
            }

        btnSeleccionarImagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImage.launch(intent)
        }

        // 👉 Guardar publicación
        btnGuardar.setOnClickListener {
            val tipo = tipoAlqui.text.toString()
            val ubicacion = ubicacionAlqui.text.toString()
            val descripcion = descripcionAlqui.text.toString()
            val imagen = imagenUri?.toString() ?: ""

            if (tipo.isBlank() || ubicacion.isBlank() || descripcion.isBlank() || imagen.isBlank()) {
                Toast.makeText(this, "Completa todos los campos y selecciona una imagen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idPerfil = 1 // ⚠️ luego será dinámico según usuario logueado

            val nuevoAlquiler = Alquiler(
                tipo_alqui = tipo,
                ubicacion_alqui = ubicacion,
                imagen_alqui = imagen,
                descripcion_alqui = descripcion,
                id_perfil = idPerfil
            )

            lifecycleScope.launch {
                val db = AppDatabase.getDatabase(applicationContext)
                db.alquilerDAO().insertAlquiler(nuevoAlquiler)

                runOnUiThread {
                    Toast.makeText(this@AddpublicacionActivity, "Publicación guardada ✅", Toast.LENGTH_SHORT).show()

                    // 👉 Limpiar campos después de guardar
                    tipoAlqui.text.clear()
                    ubicacionAlqui.text.clear()
                    descripcionAlqui.text.clear()
                    imagenAlqui.setImageResource(android.R.color.darker_gray)
                    imagenUri = null
                }
            }
        }

        // 👉 navegación inferior
        btnInicio.setOnClickListener {
            val intentinicio = Intent(this, HomeActivity::class.java)
            startActivity(intentinicio)
        }
        btnVerMiPerfil.setOnClickListener {
            val intentperfil = Intent(this, PerfilActivity::class.java)
            startActivity(intentperfil)
        }
        btnMisPublicabiones.setOnClickListener {
            val intentmispublicaciones = Intent(this, MispublicacionesActivity::class.java)
            startActivity(intentmispublicaciones)
        }
    }
}