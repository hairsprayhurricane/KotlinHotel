package com.example.kotlinhotel.presentation.hotelinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinhotel.databinding.ItemEventBinding
import com.example.kotlinhotel.domain.model.Event

class EventAdapter : ListAdapter<Event, EventAdapter.ViewHolder>(DIFF) {

    inner class ViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.tvEventTitle.text = event.title
            binding.tvEventTime.text = event.time
            binding.tvEventLocation.text = event.location
            binding.tvEventDescription.text = event.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(a: Event, b: Event) = a.id == b.id
            override fun areContentsTheSame(a: Event, b: Event) = a == b
        }
    }
}
