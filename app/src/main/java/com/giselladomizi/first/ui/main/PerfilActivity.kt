package com.giselladomizi.first.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giselladomizi.first.R
import com.giselladomizi.first.data.local.databese.AppDatabase
import kotlinx.coroutines.launch

class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ActivityCollector.addActivity(this)
        setContentView(R.layout.activity_perfil)

        val rvDatosCuenta = findViewById<RecyclerView>(R.id.rvDatosCuenta)
        rvDatosCuenta.layoutManager = LinearLayoutManager(this)
        rvDatosCuenta.setHasFixedSize(true)

        // Recuperar sesión
        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val idUsuario = prefs.getInt("id_usuario", -1)

        if (idUsuario != -1) {
            val db = AppDatabase.getDatabase(this)

            lifecycleScope.launch {
                val perfil = db.perfilDAO().getPerfilByUserId(idUsuario)

                perfil?.let {
                    rvDatosCuenta.adapter = PerfilAdapter(listOf(it))
                }
            }
        }

        //Botones de Navegación
        val btnInicio: ImageButton = findViewById(R.id.btnInicio)
        val btnAddPublicacion: ImageButton = findViewById(R.id.btnAddPublicacion)
        val btnMisPublicabiones: ImageButton = findViewById(R.id.btnMisPublicabiones)
        val cerrarSesion: TextView = findViewById(R.id.cerrarSesion)

        btnInicio.setOnClickListener{
            val intentinicio = Intent(this, HomeActivity::class.java)
            startActivity(intentinicio)
        }
        btnAddPublicacion.setOnClickListener{
            val intentaddpublicacion = Intent(this, AddpublicacionActivity::class.java)
            startActivity(intentaddpublicacion)
        }
        btnMisPublicabiones.setOnClickListener {
            val intentmispublicaciones = Intent(this, MispublicacionesActivity::class.java)
            startActivity(intentmispublicaciones)
        }
        //Cerrar sesión
        cerrarSesion.setOnClickListener {
            prefs.edit().clear().apply() // Elimina la sesión guardada

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Evito que vuelva al perfil
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}