package com.example.parcial2eventos.ejercicio2.ui.registroevento


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial2eventos.R
import com.example.parcial2eventos.ejercicio2.data.model.Evento
import com.example.parcial2eventos.ejercicio2.data.repository.EventoRepository
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class RegistroEventoActivity : AppCompatActivity() {

    private lateinit var repository: EventoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_evento)

        supportActionBar?.elevation = 0f

        // Inicializar el repositorio
        repository = EventoRepository(FirebaseFirestore.getInstance())

        // Configurar botones
        findViewById<Button>(R.id.btnGuardar).setOnClickListener { guardarEvento() }
        findViewById<Button>(R.id.btnCancelar).setOnClickListener { finish() }
    }

    private fun guardarEvento() {
        // Obtener valores de los campos
        val nombre = findViewById<EditText>(R.id.etNombre).text.toString()
        val descripcion = findViewById<EditText>(R.id.etDescripcion).text.toString()
        val direccion = findViewById<EditText>(R.id.etDireccion).text.toString()
        val precioText = findViewById<EditText>(R.id.etPrecio).text.toString()
        val precio = precioText.toDoubleOrNull() ?: 0.0
        val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        // Validar campos
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, getString(R.string.campos_obligatorios), Toast.LENGTH_SHORT).show()
            return
        }

        // Crear el objeto Evento
        val evento = Evento(
            id = UUID.randomUUID().toString(),
            nombre = nombre,
            descripcion = descripcion,
            direccion = direccion,
            precio = precio,
            fecha = fecha
        )

        // Guardar en Firestore usando el repositorio
        repository.saveEvento(evento)
            .addOnSuccessListener {
                Toast.makeText(this, getString(R.string.evento_guardado), Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, getString(R.string.error_guardar_evento), Toast.LENGTH_SHORT).show()
            }
    }
}
