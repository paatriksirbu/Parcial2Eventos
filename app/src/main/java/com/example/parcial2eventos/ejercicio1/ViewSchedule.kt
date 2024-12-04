package com.example.parcial2eventos.ejercicio1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parcial2eventos.R
import com.example.parcial2eventos.ejercicio1.model.Clase
import com.google.firebase.firestore.FirebaseFirestore

class ViewSchedule : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dayButtons : List<Button>
    private lateinit var adapter: ScheduleAdapter
    private lateinit var claseViewModel: ClaseViewModel
    private var classesList: List<Clase> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_schedule)

        supportActionBar?.elevation = 0f

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewSchedule)

        // Configurar RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ScheduleAdapter(emptyList())
        recyclerView.adapter = adapter

        // Inicializar ViewModel
        val repository = ClaseRepository(FirebaseFirestore.getInstance())
        val factory = ClaseViewModelFactory(repository)
        claseViewModel = ViewModelProvider(this, factory)[ClaseViewModel::class.java]

        // Resaltar el día actual y cargar clases
        val currentDay = getDayName(java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK))
        loadScheduleForDay(currentDay)

        // Configurar botones de dias.
        dayButtons = listOf(
            findViewById(R.id.btnMonday),
            findViewById(R.id.btnTuesday),
            findViewById(R.id.btnWednesday),
            findViewById(R.id.btnThursday),
            findViewById(R.id.btnFriday)
        )

        // Resaltamos el dia actual
        highlightCurrentDay()

    }

    private fun loadScheduleForDay(dia: String) {
        // Cargar datos de Firestore usando el ViewModel
        claseViewModel.cargarClasesPorDia(dia).observe(this) { clases ->
            if (clases.isEmpty()) {
                Log.d("RecyclerView", "No hay clases para $dia")
            } else {
                Log.d("RecyclerView", "Clases para mostrar: $clases")
            }
            adapter.updateClasses(clases)   // Actualizar el adaptador con las clases obtenidas.
        }
    }

    private fun highlightCurrentDay() {
        // Obtener el dia actual
        val currentDay = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK)

        // Mapear el dia actual al botón correspondiente
        val defaultDay = when (currentDay) {
            java.util.Calendar.MONDAY -> 0
            java.util.Calendar.TUESDAY -> 1
            java.util.Calendar.WEDNESDAY -> 2
            java.util.Calendar.THURSDAY -> 3
            java.util.Calendar.FRIDAY -> 4
            else -> 3 // Jueves por defecto si el día no es válido
        }

        // Resaltar el botón correspondiente
        highlightSelectedButton(dayButtons[defaultDay])

        // Cargar horario para el día actual
        val dayName = getDayName(defaultDay)
        loadScheduleForDay(dayName)

        // Configurar clics en botones de días
        dayButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                highlightSelectedButton(button)
                loadScheduleForDay(getDayName(index))
            }
        }
    }

    private fun highlightSelectedButton(button: Button) {
        // Restaurar el color de los botones
        dayButtons.forEach { it.setBackgroundColor(getColor(android.R.color.transparent)) }

        // Resaltar el botón seleccionado
        button.setBackgroundColor(getColor(R.color.teal_200))
    }

    private fun getDayName(index: Int): String {
        return when (index) {
            0 -> "Lunes"
            1 -> "Martes"
            2 -> "Miércoles"
            3 -> "Jueves"
            4 -> "Viernes"
            else -> ""
        }
    }
}