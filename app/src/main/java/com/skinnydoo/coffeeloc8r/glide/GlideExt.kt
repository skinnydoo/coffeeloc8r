package com.skinnydoo.coffeeloc8r.glide

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


fun <T> GlideRequest<T>.roundedCorners(cornersRadius: Float, fitCenter: Boolean = false): GlideRequest<T> {
    val options = RequestOptions()
        .transform(
            if (fitCenter) FitCenter() else CenterCrop(),
            RoundedCorners(cornersRadius.toInt())
        )
    return apply(options)
}
