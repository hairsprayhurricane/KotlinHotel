package com.example.kotlinhotel.presentation.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinhotel.databinding.ItemReviewBinding
import com.example.kotlinhotel.domain.model.Review
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReviewAdapter : ListAdapter<Review, ReviewAdapter.ViewHolder>(DIFF) {

    inner class ViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            binding.tvAuthorName.text = review.authorName
            binding.tvReviewText.text = review.text
            binding.ratingBar.rating = review.rating
            val fmt = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            binding.tvReviewDate.text = fmt.format(Date(review.date))
            if (review.isOwn) {
                binding.tvOwnLabel.visibility = android.view.View.VISIBLE
            } else {
                binding.tvOwnLabel.visibility = android.view.View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(a: Review, b: Review) = a.id == b.id
            override fun areContentsTheSame(a: Review, b: Review) = a == b
        }
    }
}
