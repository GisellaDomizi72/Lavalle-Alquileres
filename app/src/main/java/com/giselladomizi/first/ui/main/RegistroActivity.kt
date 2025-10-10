package com.giselladomizi.first.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.giselladomizi.first.R
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.giselladomizi.first.data.local.databese.AppDatabase
import com.giselladomizi.first.data.local.entity.Usuario
import com.giselladomizi.first.data.local.entity.Perfil
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        //Campos del Layout
        val etNombre: EditText = findViewById(R.id.etNombre)
        val etApellido: EditText = findViewById(R.id.etApellido)
        val etCorreo: EditText = findViewById(R.id.etCorreo)
        val etTelefono: EditText = findViewById(R.id.etTelefono)
        val etUsuario: EditText = findViewById(R.id.etUsuario)
        val etContrasena: EditText = findViewById(R.id.etContrasena)
        val btnRegistro: Button = findViewById(R.id.btnRegistro)

        // Llamo la instancia de la BD
        val db = AppDatabase.getDatabase(this)

        //Funcion del Button Registro
        btnRegistro.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val usuario = etUsuario.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() ||
                telefono.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()
            ) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }else {
                lifecycleScope.launch {
                    // Verificar si el usuario ya existe
                    val usuarioExistente = db.usuarioDAO().getUsuarioByName(usuario)

                    if (usuarioExistente != null) {
                        runOnUiThread {
                            Toast.makeText(this@RegistroActivity, "El usuario ya está registrado, elige otro nombre", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Hashear la contraseña antes de guardar
                        val hashedPassword = BCrypt.hashpw(contrasena, BCrypt.gensalt())

                        // Insertar usuario y obtener el ID autogenerado
                        val idUsuario = db.usuarioDAO().insertUsuario(
                            Usuario(name_u = usuario, passw_u = hashedPassword)
                        ).toInt()

                        // Insertar perfil vinculado al usuario
                        db.perfilDAO().insertPerfil(
                            Perfil(
                                nombre_p = nombre,
                                apellido_p = apellido,
                                correo_p = correo,
                                telefono_p = telefono,
                                id_user = idUsuario
                            )
                        )

                        runOnUiThread {
                            Toast.makeText(this@RegistroActivity, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
                            val intentLogin = Intent(this@RegistroActivity, MainActivity::class.java)
                            startActivity(intentLogin)
                            finish()
                        }
                    }
                }
            }
        }
    }
}