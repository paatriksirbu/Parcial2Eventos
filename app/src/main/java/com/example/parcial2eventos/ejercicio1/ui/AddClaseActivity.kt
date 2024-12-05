package com.example.parcial2eventos.ejercicio1.data.ui

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial2eventos.R
import com.example.parcial2eventos.ejercicio1.data.model.Clase
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.FirebaseFirestore

class AddClaseActivity : AppCompatActivity() {

    private lateinit var spAsignatura: Spinner
    private lateinit var spDia: Spinner
    private lateinit var spHora: Spinner

    private val firebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_clase)

        // Inicializar vistas
        spAsignatura = findViewById(R.id.spAsignatura)
        spDia = findViewById(R.id.spDia)
        spHora = findViewById(R.id.spHora)

        // Configurar los Spinners
        setupSpinners()

        findViewById<MaterialButton>(R.id.btnGuardar).setOnClickListener {
            saveClase()
        }

        findViewById<MaterialButton>(R.id.btnCancelar).setOnClickListener {
            finish() // Cierra la actividad
        }
    }

    private fun setupSpinners() {
        // Lista de asignaturas
        val asignaturas = listOf(
            "Administracion de Redes",
            "Administracion de Sistemas",
            "Arquitectura de Computadores",
            "Ingenieria del Software",
            "Inteligencia Artificial",
            "Interaccion Hombre-Maquina",
            "Planificacion y Gestion de Sistemas de Informacion",
            "Programacion Concurrente",
            "Programacion Dirigida por Eventos",
            "Sistemas Operativos Avanzados"
        )

        val dias = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")
        val horas = listOf(
            "08:30 - 09:30", "09:30 - 10:30", "10:30 - 11:30", "11:30 - 12:30", "12:30 - 13:30", "13:30 - 14:30",
            "15:30 - 16:30", "16:30 - 17:30", "17:30 - 18:30", "18:30 - 19:30", "19:30 - 20:30"
        )

        // Configurar adaptadores
        spAsignatura.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, asignaturas)
        spDia.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, dias)
        spHora.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, horas)
    }

    private fun saveClase() {
        // Obtener valores seleccionados
        val asignatura = spAsignatura.selectedItem.toString()
        val dia = spDia.selectedItem.toString()
        val hora = spHora.selectedItem.toString()

        // Crear objeto de la clase
        val clase = Clase(
            id = "", // No importa el ID, Firebase lo generará automáticamente
            nombre = asignatura,
            dia = dia,
            horaInicio = hora.split(" - ")[0], // Extraer hora de inicio
            horaFin = hora.split(" - ")[1]   // Extraer hora de fin
        )

        // Guardar en Firebase
        firebaseFirestore.collection("clases")
            .add(clase)
            .addOnSuccessListener {
                Toast.makeText(this, "Clase añadida exitosamente", Toast.LENGTH_SHORT).show()
                // No necesariamente se debe cerrar la actividad.
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al guardar la clase: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
                Log.e("AddClaseActivity", "Error al guardar la clase", e)
            }
    }
}

