package com.example.parcial2eventos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial2eventos.ejercicio1.data.ui.MainActivity
import com.example.parcial2eventos.ejercicio2.ui.listeventos.ListEventosActivity
import com.example.parcial2eventos.ejercicio3.ui.detail.PharmacyListActivity
import com.example.parcial2eventos.ejercicio3.ui.list.PharmacyDetailActivity

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        // Botones para redirigir a las actividades
        val btnEjercicio1 = findViewById<Button>(R.id.btnEjercicio1)
        val btnEjercicio2 = findViewById<Button>(R.id.btnEjercicio2)
        val btnEjercicio3 = findViewById<Button>(R.id.btnEjercicio3)

        // Configurar listeners para cada botón
        btnEjercicio1.setOnClickListener {
            // Redirigir a Ejercicio 1: ViewScheduleActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnEjercicio2.setOnClickListener {
            // Redirigir a Ejercicio 2: ListEventosActivity
            try {
                val intent = Intent(this, ListEventosActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error al abrir Ejercicio 2", Toast.LENGTH_SHORT).show()
            }
        }

        btnEjercicio3.setOnClickListener {
            try {
                val intent = Intent(this, PharmacyDetailActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error al abrir Ejercicio 3", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
