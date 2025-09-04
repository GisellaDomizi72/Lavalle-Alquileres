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

    // Variable para almacenar la URI de la imagen seleccionada
    private var imagenUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permite que la interfaz se muestre bajo la barra de estado y la barra de navegación
        enableEdgeToEdge()

        // Se carga el layout de la actividad
        setContentView(R.layout.activity_addpublicacion)

        // Referencias a los botones de navegación inferior
        val btnInicio: ImageButton = findViewById(R.id.btnInicio)
        val btnVerMiPerfil: ImageButton = findViewById(R.id.btnVerMiPerfil)
        val btnMisPublicabiones: ImageButton = findViewById(R.id.btnMisPublicabiones)

        // Referencias a los campos de texto y la imagen de la publicación
        val tipoAlqui = findViewById<EditText>(R.id.tipo_alqui)
        val ubicacionAlqui = findViewById<EditText>(R.id.ubicaion_alqui)
        val descripcionAlqui = findViewById<EditText>(R.id.descripcion_alqui)
        val imagenAlqui = findViewById<ImageView>(R.id.imagen_alqui)

        // Botones para seleccionar imagen y guardar publicación
        val btnSeleccionarImagen = findViewById<Button>(R.id.id_button)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarPublicacion)

        // 👉 Registrar el ActivityResult para seleccionar imágenes
        val pickImage =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // Obtener la URI de la imagen seleccionada
                    imagenUri = result.data?.data
                    // Mostrar la imagen seleccionada en el ImageView
                    imagenAlqui.setImageURI(imagenUri)
                }
            }

        // 👉 Click listener para el botón de seleccionar imagen
        btnSeleccionarImagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK) // Intent para seleccionar contenido
            intent.type = "image/*" // Solo imágenes
            pickImage.launch(intent) // Lanza el selector de imágenes
        }

        // 👉 Click listener para guardar publicación
        btnGuardar.setOnClickListener {
            val tipo = tipoAlqui.text.toString()
            val ubicacion = ubicacionAlqui.text.toString()
            val descripcion = descripcionAlqui.text.toString()
            val imagen = imagenUri?.toString() ?: "" // Convertir URI a String, si no hay, cadena vacía

            // Validar que no falten campos
            if (tipo.isBlank() || ubicacion.isBlank() || descripcion.isBlank() || imagen.isBlank()) {
                Toast.makeText(this, "Completa todos los campos y selecciona una imagen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ID de perfil fijo (temporal), luego se obtendrá del usuario logueado
            val idPerfil = 1

            // Crear objeto Alquiler con los datos ingresados
            val nuevoAlquiler = Alquiler(
                tipo_alqui = tipo,
                ubicacion_alqui = ubicacion,
                imagen_alqui = imagen,
                descripcion_alqui = descripcion,
                id_perfil = idPerfil
            )

            // Insertar en la base de datos usando coroutine
            lifecycleScope.launch {
                val db = AppDatabase.getDatabase(applicationContext)
                db.alquilerDAO().insertAlquiler(nuevoAlquiler)

                // Mostrar mensaje y limpiar campos en el hilo principal
                runOnUiThread {
                    Toast.makeText(this@AddpublicacionActivity, "Publicación guardada ✅", Toast.LENGTH_SHORT).show()

                    // Limpiar campos después de guardar
                    tipoAlqui.text.clear()
                    ubicacionAlqui.text.clear()
                    descripcionAlqui.text.clear()
                    imagenAlqui.setImageResource(android.R.color.darker_gray)
                    imagenUri = null
                }
            }
        }

        // 👉 Navegación inferior
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
