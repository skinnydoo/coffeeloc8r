package com.skinnydoo.coffeeloc8r.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.domain.home.SearchCoffeeShopUseCase
import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShop
import com.skinnydoo.coffeeloc8r.ui.home.models.HomeViewState
import com.skinnydoo.coffeeloc8r.utils.event.Event
import com.skinnydoo.coffeeloc8r.utils.extensions.exhaustive
import com.skinnydoo.coffeeloc8r.utils.mutableLiveDataOf
import com.skinnydoo.coffeeloc8r.utils.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val searchCoffeeShop: SearchCoffeeShopUseCase
) : ViewModel() {

  private val _viewState = mutableLiveDataOf<HomeViewState>()
  val viewState: LiveData<HomeViewState>
    get() = _viewState


  fun searchCoffeeShop(latLng: LatLng, accuracy: Double) {
    viewModelScope.launch {
      showLoading()

      val request = SearchCoffeeShopUseCase.Request(latLng, accuracy)
      when (val result = searchCoffeeShop(request)) {
        is Result.Success -> emitViewState(shops = result.data)
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
    shops: List<CoffeeShop>? = null
  ) {
    val newState = HomeViewState(showProgress, error, shops)
    if (viewState.value != newState) _viewState.value = newState
  }
}
