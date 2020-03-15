package com.skinnydoo.coffeeloc8r.ui.details.models

import androidx.annotation.DrawableRes

sealed class ContactType(@DrawableRes open val icon: Int) {

    data class Phone(@DrawableRes override val icon: Int) : ContactType(icon)
    data class Twitter(@DrawableRes override val icon: Int) : ContactType(icon)
    data class Facebook(@DrawableRes override val icon: Int) : ContactType(icon)
    data class Instagram(@DrawableRes override val icon: Int) : ContactType(icon)
    data class Url(@DrawableRes override val icon: Int) : ContactType(icon)
    data class FoursquareUrl(@DrawableRes override val icon: Int) : ContactType(icon)
}
