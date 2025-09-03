package com.giselladomizi.first.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.giselladomizi.first.R

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        val btnRegistro: Button = findViewById(R.id.btnRegistro)

        //Redirección para loguearse
        btnRegistro.setOnClickListener {
            Toast.makeText(this, "¡Ya te has Registrado!.", Toast.LENGTH_SHORT).show()
            // Creo intent para ir a Main
            val intentLogin = Intent(this, MainActivity::class.java)
            startActivity(intentLogin)
            finish() //cierra registro para que no se pueda volver con "atrás"
        }
    }
}