package com.skinnydoo.coffeeloc8r.ui.details.models

import com.skinnydoo.coffeeloc8r.di.scope.PerActivity
import com.skinnydoo.coffeeloc8r.ui.Actor
import com.skinnydoo.coffeeloc8r.utils.extensions.exhaustive
import javax.inject.Inject

@PerActivity
class DetailsActor @Inject constructor(private val emit: @JvmSuppressWildcards(suppress = true) (DetailsAction) -> Unit) :
    Actor {

    fun backIconClicked() = emit(DetailsAction.Back)

    fun shareIconClicked(address: String) = emit(DetailsAction.Share(address))

    fun contactClicked(contact: ContactItem) {
        when (contact.type) {
            is ContactType.Phone -> contact.text?.let { emit(DetailsAction.Call(it)) }
            else -> contact.text?.let { emit(DetailsAction.OpenUrl(it)) }
        }.exhaustive
    }

    fun poweredByClicked(url: String) = emit(DetailsAction.OpenUrl(url))

}
