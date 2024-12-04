package com.example.parcial2eventos.ejercicio1

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parcial2eventos.R
import com.example.parcial2eventos.ejercicio1.model.Clase

class ViewSchedule : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dayButtons : List<Button>
    private var classesList: List<Clase> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_schedule)

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewSchedule)

        // Configurar RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ScheduleAdapter(classesList)
        recyclerView.adapter = adapter

        // Configurar botones de dias.
        dayButtons = listOf(
            findViewById(R.id.btnMonday),
            findViewById(R.id.btnTuesday),
            findViewById(R.id.btnWednesday),
            findViewById(R.id.btnThursday),
            findViewById(R.id.btnFriday)
        )


    }
}