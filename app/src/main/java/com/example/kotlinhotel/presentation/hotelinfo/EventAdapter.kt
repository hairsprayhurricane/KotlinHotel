package com.example.kotlinhotel.presentation.hotelinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinhotel.databinding.ItemEventBinding
import com.example.kotlinhotel.domain.model.Event

class EventAdapter : ListAdapter<Event, EventAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.textEventTime.text = event.time
            binding.textEventTitle.text = event.title
            binding.textEventLocation.text = event.location
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(old: Event, new: Event) = old.id == new.id
        override fun areContentsTheSame(old: Event, new: Event) = old == new
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}
