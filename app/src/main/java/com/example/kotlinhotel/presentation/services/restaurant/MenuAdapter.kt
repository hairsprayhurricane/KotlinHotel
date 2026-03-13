package com.example.kotlinhotel.presentation.services.restaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinhotel.databinding.ItemMenuItemBinding
import com.example.kotlinhotel.domain.model.HotelService
import java.text.NumberFormat
import java.util.Locale

class MenuAdapter(
    private val onOrderClick: (HotelService) -> Unit
) : ListAdapter<HotelService, MenuAdapter.ViewHolder>(DIFF) {

    inner class ViewHolder(private val binding: ItemMenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(service: HotelService) {
            binding.tvDishName.text = service.title
            binding.tvDishDescription.text = service.description
            val fmt = NumberFormat.getNumberInstance(Locale("ru"))
            binding.tvDishPrice.text = "${fmt.format(service.price.toInt())} ₽"
            binding.btnOrderDish.setOnClickListener { onOrderClick(service) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<HotelService>() {
            override fun areItemsTheSame(a: HotelService, b: HotelService) = a.id == b.id
            override fun areContentsTheSame(a: HotelService, b: HotelService) = a == b
        }
    }
}
