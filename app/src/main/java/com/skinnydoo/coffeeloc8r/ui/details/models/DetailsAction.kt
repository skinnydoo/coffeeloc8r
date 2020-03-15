package com.skinnydoo.coffeeloc8r.ui.details.models

sealed class DetailsAction {
    object Back: DetailsAction()
    data class Share(val address: String): DetailsAction()
    data class Call(val number: String): DetailsAction()
    data class OpenUrl(val url: String): DetailsAction()
}
