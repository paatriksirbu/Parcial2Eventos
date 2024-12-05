package com.example.parcial2eventos.ejercicio1.data.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parcial2eventos.R
import com.example.parcial2eventos.ejercicio1.data.model.Clase

class ScheduleAdapter(private var classes: List<Clase>) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val clase = classes[position]
        holder.tvClassName.text = clase.nombre
        holder.tvClassTime.text = "${clase.horaInicio} - ${clase.horaFin}"
    }

    override fun getItemCount(): Int = classes.size

    fun updateClasses(newClasses: List<Clase>) {
        classes = newClasses
        notifyDataSetChanged()
    }

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvClassName: TextView = itemView.findViewById(R.id.tvClassName)
        val tvClassTime: TextView = itemView.findViewById(R.id.tvClassTime)
    }
}
