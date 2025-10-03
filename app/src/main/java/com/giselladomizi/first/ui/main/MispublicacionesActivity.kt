package com.giselladomizi.first.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.giselladomizi.first.data.local.entity.Alquiler
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giselladomizi.first.R
import com.giselladomizi.first.data.local.databese.AppDatabase
import kotlinx.coroutines.launch
//Activity donde se muestran las publicaciones del usuario logueado
class MispublicacionesActivity : AppCompatActivity() {

    private lateinit var adapter: MisPublicacionesAdapter
    private lateinit var publicacionesMias: RecyclerView
    private var perfilId: Int = 0
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ActivityCollector.addActivity(this)
        setContentView(R.layout.activity_mispublicaciones)

        // Inicializar vistas
        publicacionesMias = findViewById(R.id.publicacionesMias)

        val btnInicio: ImageButton = findViewById(R.id.btnInicio)
        val btnVerMiPerfil: ImageButton = findViewById(R.id.btnVerMiPerfil)
        val btnAddPublicacion: ImageButton = findViewById(R.id.btnAddPublicacion)

        btnInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        btnVerMiPerfil.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }
        btnAddPublicacion.setOnClickListener {
            startActivity(Intent(this, AddpublicacionActivity::class.java))
        }

        // Recuperar id de usuario logueado
        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        perfilId = prefs.getInt("id_usuario", 0)

        // Configurar RecyclerView
        publicacionesMias.layoutManager = LinearLayoutManager(this)

        // Base de datos
        db = AppDatabase.getDatabase(applicationContext)
    }

    // Se ejecuta cada vez que la activity vuelve a primer plano
    override fun onResume() {
        super.onResume()
        cargarPublicaciones()
    }

    private fun cargarPublicaciones() {
        lifecycleScope.launch {
            val alquileres = db.alquilerDAO().getAlquileresByPerfil(perfilId)

            adapter = MisPublicacionesAdapter(
                alquileres.toMutableList(),
                onDeleteClick = { alquiler ->
                    val builder = androidx.appcompat.app.AlertDialog.Builder(this@MispublicacionesActivity)
                    builder.setTitle("Confirmar eliminación")
                    builder.setMessage("¿Seguro que deseas eliminar esta publicación?")
                    builder.setPositiveButton("SI") { _, _ ->
                        lifecycleScope.launch {
                            db.alquilerDAO().deleteAlquiler(alquiler)
                            adapter.removeItem(alquiler)
                            Toast.makeText(this@MispublicacionesActivity, "Publicación eliminada con éxito", Toast.LENGTH_SHORT).show()
                        }
                    }
                    builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                    builder.create().show()
                },
                onEditClick = { alquiler ->
                    val intent = Intent(this@MispublicacionesActivity, EditarAlquilerActivity::class.java)
                    intent.putExtra("id_alqui", alquiler.id_alqui)
                    startActivity(intent)
                }
            )

            publicacionesMias.adapter = adapter
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}



