package com.matheusxreis.moviedroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.moviedroid.databinding.CreditsRowLayoutBinding
import com.matheusxreis.moviedroid.models.Cast

class CreditsAdapter: RecyclerView.Adapter<CreditsAdapter.MyViewHolder>() {

    private var persons: List<Cast> = listOf()

    class MyViewHolder(private val binding:CreditsRowLayoutBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Cast) {
            binding.cast = person
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CreditsRowLayoutBinding.inflate(layoutInflater)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPerson = persons[position]
        holder.bind(currentPerson)
    }

    override fun getItemCount(): Int  = persons.size

    fun setData(persons:List<Cast>){
        this.persons = persons
        notifyItemInserted(itemCount)
    }
}