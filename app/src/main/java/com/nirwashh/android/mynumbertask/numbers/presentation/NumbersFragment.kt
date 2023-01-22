package com.nirwashh.android.mynumbertask.numbers.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nirwashh.android.mynumbertask.databinding.FragmentNumbersBinding
import com.nirwashh.android.mynumbertask.main.presentation.BaseFragment

class NumbersFragment : BaseFragment<NumbersViewModel.Base>() {
    private var _binding: FragmentNumbersBinding? = null
    private val binding get() = _binding!!
    override val viewModelClass = NumbersViewModel.Base::class.java

    private val watcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) = viewModel.clearError()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNumbersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = NumbersAdapter(object : ClickListener {
            override fun click(item: NumberUi) = viewModel.showDetails(item)
        })
        binding.historyRecyclerView.adapter = adapter

        binding.getFactButton.setOnClickListener {
            val number = binding.editText.text.toString()
            viewModel.fetchNumberFact(number)
        }
        binding.getRandomFactButton.setOnClickListener {
            viewModel.fetchRandomNumberFact()
        }

        viewModel.observeState(this) {
            it.apply(binding.textInputLayout, binding.editText)
        }

        viewModel.observeList(this) {
            adapter.map(it)
        }

        viewModel.observeProgress(this) {
            binding.backgroundSheet.visibility = it
        }
        viewModel.init(savedInstanceState == null)

    }

    override fun onResume() {
        super.onResume()
        binding.editText.addTextChangedListener(watcher)
    }

    override fun onPause() {
        super.onPause()
        binding.editText.removeTextChangedListener(watcher)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

abstract class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    override fun afterTextChanged(s: Editable?) = Unit
}