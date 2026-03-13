package com.example.kotlinhotel.presentation.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinhotel.databinding.ItemRoomCardBinding
import com.example.kotlinhotel.domain.model.Room

class RoomAdapter(
    private val onRoomClick: (Room) -> Unit
) : ListAdapter<Room, RoomAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemRoomCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(room: Room, onClick: (Room) -> Unit) {
            binding.textRoomType.text = room.type
            binding.textRoomNumber.text = "№ ${room.number}"
            binding.textRoomPrice.text = "${room.pricePerNight.toInt()} ₽/ночь"
            binding.textRoomDesc.text = room.description
            binding.textFloor.text = "Этаж ${room.floor}"
            binding.textGuests.text = "👥 до ${room.maxGuests} гостей"
            binding.root.setOnClickListener { onClick(room) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Room>() {
        override fun areItemsTheSame(old: Room, new: Room) = old.id == new.id
        override fun areContentsTheSame(old: Room, new: Room) = old == new
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRoomCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onRoomClick)
    }
}
