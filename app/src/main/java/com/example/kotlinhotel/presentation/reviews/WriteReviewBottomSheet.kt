package com.example.kotlinhotel.presentation.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.kotlinhotel.databinding.FragmentWriteReviewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class WriteReviewBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentWriteReviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReviewsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWriteReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmitReview.setOnClickListener {
            val text = binding.etReviewText.text.toString().trim()
            val rating = binding.ratingBarInput.rating
            if (text.isBlank()) {
                Snackbar.make(binding.root, "Please write something", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.submitReview(text, rating)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "WriteReviewBottomSheet"
    }
}
