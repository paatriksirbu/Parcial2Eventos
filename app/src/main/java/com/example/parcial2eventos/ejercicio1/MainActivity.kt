package com.example.parcial2eventos.ejercicio1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.parcial2eventos.R
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ClaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViewModel()
        setupButtons()
    }


    private fun initializeViewModel(){
        val claseRepository = ClaseRepository(FirebaseFirestore.getInstance())
        val factory = ClaseViewModelFactory(claseRepository)
        viewModel = ViewModelProvider(this, factory).get(ClaseViewModel::class.java)
    }

    private fun setupButtons(){
        findViewById<Button>(R.id.btnAddClase).setOnClickListener {
            val intent = Intent(this, AddClaseActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnViewClases).setOnClickListener {
            val intent = Intent(this, ViewSchedule::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnQueToca).setOnClickListener {
            val intent = Intent(this, QueTocaActivity::class.java)
            startActivity(intent)
        }
    }
}