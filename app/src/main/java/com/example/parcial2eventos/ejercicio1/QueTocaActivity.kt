package com.example.parcial2eventos.ejercicio1


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial2eventos.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class QueTocaActivity : AppCompatActivity() {

    private lateinit var tvFechaHora: TextView
    private lateinit var tvClaseActual: TextView
    private val firestore = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_que_toca)

        supportActionBar?.elevation = 0f

        // Inicializar vistas
        tvFechaHora = findViewById(R.id.tvFechaHora)
        tvClaseActual = findViewById(R.id.tvClaseActual)

        // Mostrar la fecha y hora actual
        mostrarFechaHoraActual()

        // Consultar Firestore para obtener la clase actual
        obtenerClaseActual()
    }

    private fun mostrarFechaHoraActual() {
        val calendar = Calendar.getInstance()
        val formatoFechaHora = SimpleDateFormat("EEEE, d 'de' MMMM yyyy HH:mm", Locale("es", "ES"))
        val fechaHoraActual = formatoFechaHora.format(calendar.time)
        tvFechaHora.text = fechaHoraActual
    }

    private fun obtenerClaseActual() {
        val calendar = Calendar.getInstance()

        // Día de la semana en texto
        val diaSemana = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "Lunes"
            Calendar.TUESDAY -> "Martes"
            Calendar.WEDNESDAY -> "Miércoles"
            Calendar.THURSDAY -> "Jueves"
            Calendar.FRIDAY -> "Viernes"
            else -> null // No hay clases sábado ni domingo
        }

        if (diaSemana == null) {
            tvClaseActual.text = "No hay clases hoy."
            return
        }

        // Hora actual
        val formatoHora = SimpleDateFormat("HH:mm", Locale.getDefault())
        val horaActual = formatoHora.format(calendar.time)

        // Consultar Firestore para encontrar la clase actual
        firestore.collection("clases")
            .whereEqualTo("dia", diaSemana)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null && !snapshot.isEmpty) {
                    // Buscar la clase correspondiente a la hora actual
                    val claseActual = snapshot.documents.firstNotNullOfOrNull { doc ->
                        val horaInicio = doc.getString("horaInicio")
                        val horaFin = doc.getString("horaFin")
                        val nombre = doc.getString("nombre")
                        if (horaInicio != null && horaFin != null && nombre != null) {
                            // Compara si la hora actual está dentro del rango
                            if (horaActual >= horaInicio && horaActual <= horaFin) {
                                nombre // Devuelve el nombre de la clase si coincide
                            } else null
                        } else null
                    }

                    // Mostrar la clase actual o indicar que no hay clase
                    if (claseActual != null) {
                        tvClaseActual.text = "Estás en clase de: $claseActual"
                    } else {
                        tvClaseActual.text = "No hay clase a esta hora."
                    }
                } else {
                    tvClaseActual.text = "No hay clases programadas para hoy."
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error al obtener las clases", e)
                tvClaseActual.text = "Error al consultar las clases."
            }
    }
}
