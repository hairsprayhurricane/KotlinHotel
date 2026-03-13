package com.example.kotlinhotel.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinhotel.databinding.ItemRecommendationBinding
import com.example.kotlinhotel.domain.model.HotelService

class RecommendationAdapter(
    private val onItemClick: (HotelService) -> Unit
) : ListAdapter<HotelService, RecommendationAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(service: HotelService, onClick: (HotelService) -> Unit) {
            binding.textEmoji.text = service.iconEmoji.ifEmpty { "🛎️" }
            binding.textTitle.text = service.title
            binding.textPrice.text = "${service.price.toInt()} ₽"
            binding.root.setOnClickListener { onClick(service) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<HotelService>() {
        override fun areItemsTheSame(old: HotelService, new: HotelService) = old.id == new.id
        override fun areContentsTheSame(old: HotelService, new: HotelService) = old == new
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecommendationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}
