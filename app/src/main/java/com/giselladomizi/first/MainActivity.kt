package com.giselladomizi.first

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val myButton: Button = findViewById(R.id.myButton)
        //Comentario
        myButton.setOnClickListener {
            Toast.makeText(this, "¡Hola! Bienvenido a Lavalle Alquileres.", Toast.LENGTH_SHORT).show()
        }
    }
}