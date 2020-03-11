package com.skinnydoo.coffeeloc8r.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShop
import com.skinnydoo.coffeeloc8r.domain.home.SearchCoffeeShopUseCase
import com.skinnydoo.coffeeloc8r.ui.home.models.HomeViewState
import com.skinnydoo.coffeeloc8r.utils.event.Event
import com.skinnydoo.coffeeloc8r.utils.extensions.exhaustive
import com.skinnydoo.coffeeloc8r.utils.mutableLiveDataOf
import com.skinnydoo.coffeeloc8r.utils.network.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val searchCoffeeShop: SearchCoffeeShopUseCase
) : ViewModel() {

    private val _viewState = mutableLiveDataOf<HomeViewState>()
    val viewState: LiveData<HomeViewState>
        get() = _viewState


    init {
        searchCoffeeShop()
    }

    private fun searchCoffeeShop() {
        viewModelScope.launch {
            showLoading()

            when (val result = searchCoffeeShop(Unit)) {
                is Result.Success -> emitViewState(success = Event(result.data))
                is Result.Error -> TODO()
            }.exhaustive
        }
    }

    private fun showLoading() {
        emitViewState(showProgress = true)
    }

    private fun emitViewState(
        showProgress: Boolean = false,
        error: Event<Int>? = null,
        success: Event<List<CoffeeShop>>? = null
    ) {
        val newState = HomeViewState(showProgress, error, success)
        if (viewState.value != newState) _viewState.value = newState
    }
}
