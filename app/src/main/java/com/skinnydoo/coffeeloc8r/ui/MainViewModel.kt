package com.skinnydoo.coffeeloc8r.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.skinnydoo.coffeeloc8r.ui.details.models.DetailsAction
import com.skinnydoo.coffeeloc8r.ui.home.models.HomeAction
import com.skinnydoo.coffeeloc8r.utils.event.Event
import com.skinnydoo.coffeeloc8r.utils.mutableLiveDataOf
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {

    private val _homeActions = mutableLiveDataOf<Event<HomeAction>>()
    val homeActions: LiveData<Event<HomeAction>>
        get() = _homeActions

    private val _detailsAction = mutableLiveDataOf<Event<DetailsAction>>()
    val detailsAction: LiveData<Event<DetailsAction>>
        get() = _detailsAction


    fun emitHomeAction(action: HomeAction) {
        _homeActions.value = Event(action)
    }


    fun emitDetailsViewAction(action: DetailsAction) {
        _detailsAction.value = Event(action)
    }
}
