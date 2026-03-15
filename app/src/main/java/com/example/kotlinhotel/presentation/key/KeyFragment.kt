package com.example.kotlinhotel.presentation.key

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlinhotel.R
import com.example.kotlinhotel.databinding.FragmentKeyBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KeyFragment : Fragment() {

    private var _binding: FragmentKeyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: KeyViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKeyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTapPhone.setOnClickListener {
            viewModel.simulateUnlock()
        }
        observeState()
    }

    fun handleNfcIntent(intent: Intent) {
        val action = intent.action
        if (action == NfcAdapter.ACTION_NDEF_DISCOVERED || action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            viewModel.simulateUnlock()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.roomNumber.collect { number ->
                        binding.tvRoomNumber.text = "Room $number"
                    }
                }
                launch {
                    viewModel.doorState.collect { state ->
                        when (state) {
                            DoorState.LOCKED -> {
                                binding.tvDoorStatus.text = "Locked"
                                binding.tvDoorStatus.setTextColor(
                                    ContextCompat.getColor(requireContext(), R.color.colorTextSecondary)
                                )
                                binding.btnTapPhone.isEnabled = true
                                binding.progressUnlocking.visibility = View.GONE
                            }
                            DoorState.UNLOCKING -> {
                                binding.tvDoorStatus.text = "Unlocking…"
                                binding.tvDoorStatus.setTextColor(
                                    ContextCompat.getColor(requireContext(), R.color.colorAccent)
                                )
                                binding.btnTapPhone.isEnabled = false
                                binding.progressUnlocking.visibility = View.VISIBLE
                            }
                            DoorState.OPEN -> {
                                binding.tvDoorStatus.text = "Door Open!"
                                binding.tvDoorStatus.setTextColor(
                                    ContextCompat.getColor(requireContext(), R.color.colorAccent)
                                )
                                binding.progressUnlocking.visibility = View.GONE
                                Snackbar.make(binding.root, "Door Opened!", Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
