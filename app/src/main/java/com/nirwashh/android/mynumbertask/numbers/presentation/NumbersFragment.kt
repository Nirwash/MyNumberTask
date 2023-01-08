package com.nirwashh.android.mynumbertask.numbers.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nirwashh.android.mynumbertask.databinding.FragmentNumbersBinding
import com.nirwashh.android.mynumbertask.details.presentation.DetailsFragment
import com.nirwashh.android.mynumbertask.main.presentation.ShowFragment
import com.nirwashh.android.mynumbertask.main.sl.ProvideViewModel

class NumbersFragment : Fragment() {
    private var _binding: FragmentNumbersBinding? = null
    private val binding get() = _binding!!
    private var showFragment: ShowFragment = ShowFragment.Empty()
    private lateinit var viewModel: NumbersViewModel
    private val watcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) = viewModel.clearError()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showFragment = context as ShowFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as ProvideViewModel).provideViewModel(
            NumbersViewModel::class.java,
            this
        )
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
            override fun click(item: NumberUi) {
                val mapper = DetailUi()
                val text = item.map(mapper)
                val detailsFragment = DetailsFragment.newInstance(text)
                showFragment.show(detailsFragment)
            }
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

    override fun onDetach() {
        super.onDetach()
        showFragment = ShowFragment.Empty()
    }
}

abstract class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    override fun afterTextChanged(s: Editable?) = Unit
}