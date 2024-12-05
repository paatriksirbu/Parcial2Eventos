package com.example.parcial2eventos.ejercicio1.data.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parcial2eventos.R
import com.example.parcial2eventos.ejercicio1.data.repository.ClaseRepository
import com.example.parcial2eventos.ejercicio1.data.model.Clase
import com.example.parcial2eventos.ejercicio1.data.viewmodel.ClaseViewModel
import com.example.parcial2eventos.ejercicio1.data.viewmodel.ClaseViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

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
        val currentDay = getDayName(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
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
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

        // Mapear el dia actual al botón correspondiente
        val defaultDay = when (currentDay) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
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