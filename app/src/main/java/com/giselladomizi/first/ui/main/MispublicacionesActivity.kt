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
    //Adaptador del RecyclerView
    private lateinit var adapter: MisPublicacionesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() //Activa el soporte para que la UI use toda la pantalla
        setContentView(R.layout.activity_mispublicaciones)

        val btnInicio: ImageButton = findViewById(R.id.btnInicio)
        val btnVerMiPerfil: ImageButton = findViewById(R.id.btnVerMiPerfil)
        val btnAddPublicacion: ImageButton = findViewById(R.id.btnAddPublicacion)


        btnInicio.setOnClickListener {
            val intentinicio = Intent(this, HomeActivity::class.java)
            startActivity(intentinicio)
        }
        btnVerMiPerfil.setOnClickListener {
            // Creo intent para ir a PerfilActivity
            val intentperfil = Intent(this, PerfilActivity::class.java)
            startActivity(intentperfil)
        }
        btnAddPublicacion.setOnClickListener {
            val intentaddpublicacion = Intent(this, AddpublicacionActivity::class.java)
            startActivity(intentaddpublicacion)
        }
//Recuperar el id del usuario logueado guardado en SharedPreferences
        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val perfilId = prefs.getInt("id_usuario", 0)

        //Configuracion del RecyclerView
        val publicacionesMias = findViewById<RecyclerView>(R.id.publicacionesMias)
        publicacionesMias.layoutManager = LinearLayoutManager(this)

//Obtencion de la base de datos(Room)
        val db = AppDatabase.getDatabase(applicationContext)
        //Lanzar corrutina para acceder a la bd sin bloquear LA UI
        lifecycleScope.launch {
//Obtener las publicaciones del usuario logueado(filtradas por perfilId)
            val alquileres = db.alquilerDAO().getAlquileresByPerfil(perfilId) //

//Configurar el adaptador con la lista y la accion de eliminar
            adapter = MisPublicacionesAdapter(alquileres.toMutableList()) { alquiler ->

//Crear dialogo de confirmacion
                val builder =
                    androidx.appcompat.app.AlertDialog.Builder(this@MispublicacionesActivity)
                builder.setTitle("Confirmar eliminación")
                builder.setMessage("¿Seguro que deseas eliminar esta publicación? ")
//Boton SI elimina de la bd y la lista
                builder.setPositiveButton("SI") { _, _ ->
                    lifecycleScope.launch {
                        //Eliminar la publicacion de la base de datos
                        db.alquilerDAO().deleteAlquiler(alquiler)
                        //Eliminar publicacion de la lista en pantalla
                        adapter.removeItem(alquiler)
                        Toast.makeText(this@MispublicacionesActivity,"Publicación eliminada con exito", Toast.LENGTH_SHORT).show()
                    }
                }
//Boton No- Cierra el dialogo
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }

                //Mostrar el dialogo
                builder.create().show()
            }


//Asignar adaptador al RecyclerView
            publicacionesMias.adapter = adapter


        }
    }
}



