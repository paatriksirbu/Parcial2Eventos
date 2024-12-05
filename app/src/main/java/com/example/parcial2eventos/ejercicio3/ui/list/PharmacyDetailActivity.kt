package com.example.parcial2eventos.ejercicio3.ui.list

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.parcial2eventos.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executors
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class PharmacyDetailActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val PHARMACY_COLLECTION = "Pharmacies"
        private const val GOOGLE_MAPS_API_KEY = "AIzaSyDA_2udnmh6p9-FNWRJo1gnsif1Q7aC8Iw" // <--- Inserta aquí tu API Key
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_detail)

        // Debug para verificar que la actividad se crea correctamente
        Log.d("PharmacyDetailActivity", "Actividad creada")

        // Configurar el fragmento del mapa
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Solicitar permisos de ubicación
        if (checkAndRequestPermissions()) {
            enableMyLocation()
        }

        // Centrar el mapa en una ubicación por defecto (Zaragoza)
        val defaultLocation = LatLng(41.6488226, -0.8890853) // Coordenadas de Zaragoza
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12.0f))

        // Obtener farmacias cercanas
        fetchNearbyPharmacies(defaultLocation)
    }

    private fun checkAndRequestPermissions(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            false
        } else {
            true
        }
    }

    private fun enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        }
    }

    /**
     * Obtiene las farmacias cercanas utilizando la API de Google Maps
     */
    private fun fetchNearbyPharmacies(location: LatLng) {
        val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?location=${location.latitude},${location.longitude}" +
                "&radius=2000&type=pharmacy&key=$GOOGLE_MAPS_API_KEY"

        // Ejecutar la solicitud en un hilo separado
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()

                if (response.isSuccessful && response.body != null) {
                    val responseBody = response.body!!.string()
                    val jsonObject = JSONObject(responseBody)
                    val results = jsonObject.getJSONArray("results")

                    runOnUiThread {
                        for (i in 0 until results.length()) {
                            val place = results.getJSONObject(i)
                            val name = place.getString("name")
                            val address = place.getString("vicinity")
                            val geometry = place.getJSONObject("geometry")
                            val locationObj = geometry.getJSONObject("location")
                            val lat = locationObj.getDouble("lat")
                            val lng = locationObj.getDouble("lng")

                            // Crear marcador en el mapa
                            val pharmacyLocation = LatLng(lat, lng)
                            mMap.addMarker(
                                MarkerOptions()
                                    .position(pharmacyLocation)
                                    .title(name)
                                    .snippet(address)
                            )

                            // Opción para guardar en Firestore al hacer clic
                            mMap.setOnMarkerClickListener { marker ->
                                val pharmacy = hashMapOf(
                                    "name" to marker.title,
                                    "address" to marker.snippet,
                                    "latitude" to marker.position.latitude,
                                    "longitude" to marker.position.longitude
                                )

                                showPharmacyDetails(marker.title.toString(),
                                    marker.snippet.toString(), marker.position.latitude, marker.position.longitude)

                                true
                            }
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this, "Error al obtener farmacias", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error al realizar la solicitud", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }
        }
    }

    private fun showPharmacyDetails(name: String, address: String, latitude: Double, longitude: Double) {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_pharmacy_details, null)

        bottomSheetDialog.setContentView(bottomSheetView)

        // Configurar los datos de la farmacia
        val txtName = bottomSheetView.findViewById<TextView>(R.id.txt_pharmacy_name)
        val txtAddress = bottomSheetView.findViewById<TextView>(R.id.txt_pharmacy_address)
        val txtCoordinates = bottomSheetView.findViewById<TextView>(R.id.txt_pharmacy_coordinates)
        val btnSave = bottomSheetView.findViewById<Button>(R.id.btn_save_pharmacy)

        txtName.text = name
        txtAddress.text = address
        txtCoordinates.text = "Lat: $latitude, Lng: $longitude"

        // Configurar el botón para guardar en Firestore
        btnSave.setOnClickListener {
            val pharmacy = hashMapOf(
                "name" to name,
                "address" to address,
                "latitude" to latitude,
                "longitude" to longitude
            )

            firestore.collection(PHARMACY_COLLECTION)
                .add(pharmacy)
                .addOnSuccessListener {
                    Toast.makeText(this, "Farmacia guardada en Firestore", Toast.LENGTH_SHORT).show()
                    bottomSheetDialog.dismiss()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        bottomSheetDialog.show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
