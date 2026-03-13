package com.example.kotlinhotel.presentation.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinhotel.HotelApp
import com.example.kotlinhotel.databinding.FragmentReviewsBinding
import com.example.kotlinhotel.presentation.ViewModelFactory

class ReviewsFragment : Fragment() {

    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReviewsViewModel by activityViewModels {
        ViewModelFactory((requireActivity().application as HotelApp).repository)
    }

    private lateinit var adapter: ReviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ReviewAdapter()
        binding.recyclerReviews.apply {
            this.adapter = this@ReviewsFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            adapter.submitList(reviews)
        }

        binding.fabAddReview.setOnClickListener {
            AddReviewDialogFragment().show(parentFragmentManager, "add_review")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
