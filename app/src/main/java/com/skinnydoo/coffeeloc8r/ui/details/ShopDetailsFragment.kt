package com.skinnydoo.coffeeloc8r.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.skinnydoo.coffeeloc8r.common.AppExecutors
import com.skinnydoo.coffeeloc8r.databinding.FragmentShopDetailsBinding
import com.skinnydoo.coffeeloc8r.ui.details.adapter.ShopDetailsAdapter
import com.skinnydoo.coffeeloc8r.ui.details.models.viewtype.DetailsViewTypeFactory
import com.skinnydoo.coffeeloc8r.utils.extensions.showToast
import javax.inject.Inject


class ShopDetailsFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    viewTypeFactory: DetailsViewTypeFactory,
    appExecutors: AppExecutors
) : Fragment() {

    private var _binding: FragmentShopDetailsBinding? = null
    private val binding get() = _binding!!

    private val navArgs by navArgs<ShopDetailsFragmentArgs>()
    private val viewModel by viewModels<ShopDetailsViewModel> { viewModelFactory }
    private val detailsAdapter by lazy { ShopDetailsAdapter(appExecutors, viewTypeFactory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getShopDetails(navArgs.shopId)
    }

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
        setupRecycler()
    }

    private fun setupRecycler() {
        binding.detailsRecycler.adapter = detailsAdapter
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            val uiState = state ?: return@Observer

            uiState.error?.takeIf { !it.consumed }?.consume()?.let { showToast(it) }

            uiState.items?.let {
                detailsAdapter.submitList(it)
            }
        })
    }

}
