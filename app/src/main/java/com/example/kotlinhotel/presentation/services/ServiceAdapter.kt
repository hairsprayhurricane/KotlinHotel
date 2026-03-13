package com.example.kotlinhotel.presentation.services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinhotel.databinding.ItemServiceCardBinding
import com.example.kotlinhotel.domain.model.HotelService

class ServiceAdapter(
    private val onOrderClick: (HotelService) -> Unit
) : ListAdapter<HotelService, ServiceAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemServiceCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(service: HotelService, onOrderClick: (HotelService) -> Unit) {
            binding.textServiceEmoji.text = service.iconEmoji.ifEmpty { "🛎️" }
            binding.textServiceTitle.text = service.title
            binding.textServiceDesc.text = service.description
            binding.textServicePrice.text = "${service.price.toInt()} ₽"
            binding.btnOrder.setOnClickListener { onOrderClick(service) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<HotelService>() {
        override fun areItemsTheSame(old: HotelService, new: HotelService) = old.id == new.id
        override fun areContentsTheSame(old: HotelService, new: HotelService) = old == new
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemServiceCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onOrderClick)
    }
}
