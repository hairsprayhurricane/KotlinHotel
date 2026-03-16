package com.example.kotlinhotel.presentation.rooms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinhotel.databinding.ItemRoomBinding
import com.example.kotlinhotel.domain.model.Room
import java.text.NumberFormat
import java.util.Locale

class RoomAdapter(
    private val onRoomClick: (Room) -> Unit
) : ListAdapter<Room, RoomAdapter.ViewHolder>(DIFF) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).id.hashCode().toLong()

    inner class ViewHolder(private val binding: ItemRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(room: Room) {
            binding.tvRoomName.text = "${room.type} · ${room.number}"
            binding.tvRoomFloor.text = "Floor ${room.floor}"
            val fmt = NumberFormat.getNumberInstance(Locale("ru"))
            binding.tvRoomPrice.text = "${fmt.format(room.pricePerNight.toInt())} ₽ / night"
            binding.btnBook.setOnClickListener { onRoomClick(room) }
            binding.root.setOnClickListener { onRoomClick(room) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Room>() {
            override fun areItemsTheSame(a: Room, b: Room) = a.id == b.id
            override fun areContentsTheSame(a: Room, b: Room) = a == b
        }
    }
}