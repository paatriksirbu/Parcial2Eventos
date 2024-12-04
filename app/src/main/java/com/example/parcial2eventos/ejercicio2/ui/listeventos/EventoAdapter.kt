package com.example.parcial2eventos.ejercicio2.ui.listeventos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parcial2eventos.R
import com.example.parcial2eventos.ejercicio2.data.model.Evento

class EventoAdapter(private var eventos: List<Evento>) :
    RecyclerView.Adapter<EventoAdapter.EventoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evento, parent, false)
        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val evento = eventos[position]
        holder.tvNombre.text = evento.nombre
        holder.tvDescripcion.text = evento.descripcion
        holder.tvPrecio.text = evento.precio.toString()
    }

    override fun getItemCount(): Int = eventos.size

    fun actualizarEventos(nuevosEventos: List<Evento>) {
        eventos = nuevosEventos
        notifyDataSetChanged()
    }

    class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreEvento)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcionEvento)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecioEvento)
    }
}
