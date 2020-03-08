package com.skinnydoo.coffeeloc8r.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.skinnydoo.coffeeloc8r.databinding.FragmentShopDetailsBinding
import javax.inject.Inject


class ShopDetailsFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment() {

    private var _binding: FragmentShopDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ShopDetailsViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        observeViewModel()
        initView()
    }

    private fun initView() {

    }

    private fun observeViewModel() {

    }

}
