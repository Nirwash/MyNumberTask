package com.nirwashh.android.mynumbertask.numbers.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nirwashh.android.mynumbertask.R
import com.nirwashh.android.mynumbertask.databinding.FragmentNumbersBinding
import com.nirwashh.android.mynumbertask.details.presentation.DetailsFragment
import com.nirwashh.android.mynumbertask.main.presentation.ShowFragment

class NumbersFragment : Fragment() {
    private var _binding: FragmentNumbersBinding? = null
    private val binding get() = _binding!!
    private var showFragment: ShowFragment = ShowFragment.Empty()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showFragment = context as ShowFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNumbersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.GONE
        binding.getFactButton.setOnClickListener {
            //TODO REFACTOR AND REMOVE HARDCODE
            val text = "some information about number hardcoded"
            val detailsFragment = DetailsFragment.newInstance(text)
            showFragment.show(detailsFragment)
        }
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