package com.giselladomizi.first.ui.main

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.giselladomizi.first.R
import com.giselladomizi.first.data.local.databese.AppDatabase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Verificar si hay sesión activa
        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val idUsuario = prefs.getInt("id_usuario", -1)

        if (idUsuario != -1) {
            // Usuario ya tiene sesión, ir directo al Home
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // evita volver al login con el botón atrás
            return
        }

        // Si no hay sesión, mostrar layout normal de login
        setContentView(R.layout.activity_main)
        supportActionBar?.hide() // Oculta la barra que trae por defecto

        //Campos del Layout
        val etUsuario: EditText = findViewById(R.id.etUsuario)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val registro: TextView = findViewById(R.id.registro)


        // Llamo la instancia de la BD
        val db = AppDatabase.getDatabase(this)

        //Redirección para loguearse e ir a Vista Home
        btnLogin.setOnClickListener {

            val usuario = etUsuario.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val user = db.usuarioDAO().login(usuario, password)
                    if (user != null) {
                        Toast.makeText(this@MainActivity, "¡Bienvenido a Lavalle Alquileres!", Toast.LENGTH_SHORT).show()

                        // Guardar sesión con el ID del usuario
                        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
                        prefs.edit().putInt("id_usuario", user.id_user).apply()


                        // Ir a Home
                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        //Redirección para registrarse
        registro.setOnClickListener {
            val intentregistro = Intent(this, RegistroActivity::class.java)
            startActivity(intentregistro)
        }
    }
}