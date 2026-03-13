package com.example.kotlinhotel.presentation.reviews

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.kotlinhotel.HotelApp
import com.example.kotlinhotel.databinding.DialogAddReviewBinding
import com.example.kotlinhotel.presentation.ViewModelFactory

class AddReviewDialogFragment : DialogFragment() {

    private val viewModel: ReviewsViewModel by activityViewModels {
        ViewModelFactory((requireActivity().application as HotelApp).repository)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogAddReviewBinding.inflate(LayoutInflater.from(requireContext()))

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton("Отправить") { _, _ ->
                val text = binding.editReviewText.text.toString()
                val rating = binding.ratingBarInput.rating
                if (text.isBlank()) {
                    Toast.makeText(requireContext(), "Введите текст отзыва", Toast.LENGTH_SHORT).show()
                } else {
                    val user = (requireActivity().application as HotelApp).repository
                    viewModel.addReview("Вы", rating, text)
                }
            }
            .setNegativeButton("Отмена", null)
            .create()
    }
}
