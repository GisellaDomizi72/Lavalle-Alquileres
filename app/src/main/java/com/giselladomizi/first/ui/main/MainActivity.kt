package com.giselladomizi.first.ui.main

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.TextView
import com.giselladomizi.first.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        supportActionBar?.hide() // Oculta la barra que trae por defecto

        val btnLogin: Button = findViewById(R.id.btnLogin)
        val registro: TextView = findViewById(R.id.registro)

        //Redirección para loguearse
        btnLogin.setOnClickListener {
            Toast.makeText(this, "¡Hola! Bienvenido a Lavalle Alquileres.", Toast.LENGTH_SHORT).show()
            // Creo intent para ir a HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() //cierra Login para que no se pueda volver con "atrás"
        }

        //Redirección para registrarse
        registro.setOnClickListener {
            val intentregistro = Intent(this, RegistroActivity::class.java)
            startActivity(intentregistro)
        }
    }
}