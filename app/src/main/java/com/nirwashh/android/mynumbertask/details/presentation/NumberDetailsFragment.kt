package com.nirwashh.android.mynumbertask.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nirwashh.android.mynumbertask.databinding.FragmentDetailsBinding
import com.nirwashh.android.mynumbertask.main.presentation.BaseFragment

class NumberDetailsFragment : BaseFragment<NumberDetailsViewModel>() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    override val viewModelClass = NumberDetailsViewModel::class.java

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val value = viewModel.read()
        binding.detailsTextView.text = value
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}