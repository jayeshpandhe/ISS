package com.example.iss.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iss.R
import com.example.iss.model.Astronaut

/**
 * Shows astronauts list
 */
class AstronautsListAdapter(private val astronauts: List<Astronaut>) :
    RecyclerView.Adapter<AstronautsListAdapter.AstronautsViewHolder>() {

    class AstronautsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val craft: TextView
        val astronautName: TextView

        init {
            craft = itemView.findViewById(R.id.craft)
            astronautName = itemView.findViewById(R.id.astronaut_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AstronautsViewHolder {
        val layoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = layoutInflater.inflate(R.layout.astronaut_list_item, parent, false)
        return AstronautsViewHolder(itemView)
    }

    override fun getItemCount(): Int = astronauts.size

    override fun onBindViewHolder(holder: AstronautsViewHolder, position: Int) {
        val astronaut = astronauts[position]
        val context = holder.craft.context
        holder.craft.text = context.getString(R.string.craft_name, astronaut.craft)
        holder.astronautName.text = context.getString(R.string.astronaut_name, astronaut.name)
    }
}