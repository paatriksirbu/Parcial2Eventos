package com.example.parcial2eventos.ejercicio3.ui.detail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parcial2eventos.R
import com.example.parcial2eventos.ejercicio3.data.model.Pharmacy
import com.example.parcial2eventos.ejercicio3.ui.list.PharmacyDetailActivity

class PharmacyAdapter(
    private val context: Context,
    private val pharmacyList: List<Pharmacy>
) : RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pharmacy, parent, false)
        return PharmacyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PharmacyViewHolder, position: Int) {
        val pharmacy = pharmacyList[position]
        holder.txtName.text = pharmacy.name
        holder.txtAddress.text = pharmacy.address

        holder.itemView.setOnClickListener {
            val intent = Intent(context, PharmacyDetailActivity::class.java).apply {
                putExtra("nombre", pharmacy.name)
                putExtra("direccion", pharmacy.address)
                putExtra("latitud", pharmacy.latitude)
                putExtra("longitud", pharmacy.longitude)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = pharmacyList.size

    class PharmacyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txt_name)
        val txtAddress: TextView = itemView.findViewById(R.id.txt_address)
    }
}
