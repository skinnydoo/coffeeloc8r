package com.skinnydoo.coffeeloc8r.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.common.AppExecutors
import com.skinnydoo.coffeeloc8r.databinding.FragmentShopDetailsBinding
import com.skinnydoo.coffeeloc8r.ui.MainViewModel
import com.skinnydoo.coffeeloc8r.ui.details.adapter.ShopDetailsAdapter
import com.skinnydoo.coffeeloc8r.ui.details.models.DetailsAction
import com.skinnydoo.coffeeloc8r.ui.details.models.DetailsActor
import com.skinnydoo.coffeeloc8r.ui.details.models.viewtype.DetailsViewTypeFactory
import com.skinnydoo.coffeeloc8r.utils.AppBarState
import com.skinnydoo.coffeeloc8r.utils.event.EventObserver
import com.skinnydoo.coffeeloc8r.utils.extensions.*
import javax.inject.Inject


class ShopDetailsFragment @Inject constructor(
    viewModelFactory: ViewModelProvider.Factory,
    viewTypeFactory: DetailsViewTypeFactory,
    appExecutors: AppExecutors
) : Fragment() {

    private var _binding: FragmentShopDetailsBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("_binding not initialized")


    private val navArgs by navArgs<ShopDetailsFragmentArgs>()
    private val viewModel by viewModels<ShopDetailsViewModel> { viewModelFactory }
    private val activityViewModel by activityViewModels<MainViewModel>()
    private val actor by lazy { DetailsActor(activityViewModel::emitDetailsViewAction) }
    private val detailsAdapter by lazy { ShopDetailsAdapter(appExecutors, viewTypeFactory, actor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getShopDetails(navArgs.shop.id)
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
        binding.shop = navArgs.shop
        binding.actor = actor
        observeViewModel()
        initView()
    }


    private fun initView() {
        setupCollapsingToolbar()
        setupRecycler()
    }

    private fun setupRecycler() {
        binding.detailsRecycler.adapter = detailsAdapter
    }

    private fun setupCollapsingToolbar() {
        binding.appBar.addOnOffsetChangedListener(stateChanged = { _, state ->
            when (state) {
                AppBarState.COLLAPSED -> {
                    binding.coffeeCupIc.animate()
                        .alpha(0f)
                        .setDuration(
                            resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
                        )
                        .setListener(animationEnd = {
                            binding.coffeeCupIc.remove()
                        })
                }
                else -> {
                    binding.coffeeCupIc.animate()
                        .alpha(1f)
                        .setDuration(
                            resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
                        )
                        .setListener(animationEnd = {
                            binding.coffeeCupIc.show()
                        })
                }
            }
        })
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            val uiState = state ?: return@Observer

            uiState.error?.takeIf { !it.consumed }?.consume()?.let { showToast(it) }

            uiState.success?.let {
                detailsAdapter.submitList(it.items)
            }
        })

        activityViewModel.detailsAction.observe(
            viewLifecycleOwner,
            EventObserver(this::handleDetailsAction)
        )

    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun handleDetailsAction(action: DetailsAction) {
        when (action) {
            DetailsAction.Back -> requireActivity().onBackPressed()
            is DetailsAction.Share -> context?.shareWithChooser(
                action.address,
                getString(R.string.app_name),
                getString(R.string.share_title)
            )
            is DetailsAction.Call -> context?.dial(action.number)
            is DetailsAction.OpenUrl -> context?.browse(action.url)
        }.exhaustive
    }
}
