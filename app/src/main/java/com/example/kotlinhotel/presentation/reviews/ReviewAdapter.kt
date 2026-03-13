package com.example.kotlinhotel.presentation.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinhotel.databinding.ItemReviewBinding
import com.example.kotlinhotel.domain.model.Review

class ReviewAdapter : ListAdapter<Review, ReviewAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.textAvatar.text = review.authorName.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
            binding.textAuthor.text = review.authorName
            binding.textDate.text = review.date
            binding.ratingBar.rating = review.rating
            binding.textReviewText.text = review.text
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(old: Review, new: Review) = old.id == new.id
        override fun areContentsTheSame(old: Review, new: Review) = old == new
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}
