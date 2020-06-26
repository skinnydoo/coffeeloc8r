package com.skinnydoo.coffeeloc8r.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.domain.details.GetCoffeeShopDetailsUseCase
import com.skinnydoo.coffeeloc8r.ui.details.models.ShopDetailsViewState
import com.skinnydoo.coffeeloc8r.ui.details.models.ShopDetailsViewStateResult
import com.skinnydoo.coffeeloc8r.utils.event.Event
import com.skinnydoo.coffeeloc8r.utils.extensions.exhaustive
import com.skinnydoo.coffeeloc8r.utils.mutableLiveDataOf
import com.skinnydoo.coffeeloc8r.utils.network.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopDetailsViewModel @Inject constructor(
    private val getCoffeeShopDetails: GetCoffeeShopDetailsUseCase
) : ViewModel() {

    private val _viewState = mutableLiveDataOf<ShopDetailsViewState>()
    val viewState: LiveData<ShopDetailsViewState>
        get() = _viewState

    fun getShopDetails(shopId: String) {
        viewModelScope.launch {
            showLoading()

            val request = GetCoffeeShopDetailsUseCase.Request(shopId)
            when (val result = getCoffeeShopDetails(request)) {
                is Result.Success -> emitViewState(success = ShopDetailsViewStateResult(result.data.first, result.data.second))
                is Result.Error -> emitViewState(error = Event(R.string.error_loading_data))
            }.exhaustive
        }
    }

    private fun showLoading() {
        emitViewState(showProgress = true)
    }

    private fun emitViewState(
        showProgress: Boolean = false,
        error: Event<Int>? = null,
        success: ShopDetailsViewStateResult? = null
    ) {
        val newState = ShopDetailsViewState(showProgress, error, success)
        if (viewState.value != newState) _viewState.value = newState
    }
}
