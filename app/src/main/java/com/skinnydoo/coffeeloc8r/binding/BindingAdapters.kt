package com.skinnydoo.coffeeloc8r.binding

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.skinnydoo.coffeeloc8r.glide.GlideApp
import com.skinnydoo.coffeeloc8r.glide.roundedCorners

@BindingAdapter("goneUnless")
fun bindGoneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("visibleUnless")
fun bindVisibleUnless(view: View, gone: Boolean) {
    view.visibility = if (gone) View.GONE else View.VISIBLE
}

@BindingAdapter("goneIf")
fun bindGoneIf(view: View, gone: Boolean) {
    view.visibility = if (gone) View.GONE else View.VISIBLE
}

@BindingAdapter("visibleIf")
fun bindVisibleIf(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE

}

@BindingAdapter("hideIfZero")
fun hideIfZero(view: View, number: Int) {
    view.visibility = if (number == 0) View.GONE else View.VISIBLE
}


@BindingAdapter(
    value = ["imageUrl", "placeholder", "circle", "cornersRadius", "fitCenter"],
    requireAll = false
)
fun ImageView.loadFromUrl(
    imageUrl: String?,
    placeholder: Drawable?,
    circle: Boolean = false,
    cornersRadius: Float = 0f,
    fitCenter: Boolean = false
) {
    loadFromUri(imageUrl?.toUri(), placeholder, circle, cornersRadius, fitCenter)
}

@BindingAdapter(
    value = ["imageUri", "placeholder", "circle", "cornersRadius", "fitCenter"],
    requireAll = false
)
fun ImageView.loadFromUri(
    imageUri: Uri?,
    placeholder: Drawable?,
    circle: Boolean = false,
    cornersRadius: Float = 0f,
    fitCenter: Boolean = false
) {
    when (imageUri) {
        null -> {
            GlideApp.with(context)
                .load(placeholder)
                .into(this)
        }
        else -> {
            GlideApp.with(context)
                .load(imageUri)
                .run {
                    when {
                        circle -> circleCrop()
                        cornersRadius != 0f -> roundedCorners(cornersRadius, fitCenter)
                        fitCenter -> fitCenter()
                        else -> centerCrop()
                    }
                }.into(this)
        }
    }
}

@BindingAdapter("drawableRes")
fun ImageView.bindDrawableRes(@DrawableRes resId: Int) {
    setImageResource(resId)
}

@BindingAdapter("drawable")
fun ImageView.bindDrawable(drawable: Drawable?) {
    setImageDrawable(drawable)
}


/**
 * In [ProgressBar], [ProgressBar.setMax] must be called before [ProgressBar.setProgress].
 * By grouping both attributes in a BindingAdapter we can make sure the order is met.
 */
@BindingAdapter(value = ["android:max", "android:progress"], requireAll = true)
fun bindProgress(progressBar: ProgressBar, max: Int, progress: Int) {
    progressBar.max = max
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        progressBar.setProgress(progress, false)
    } else {
        progressBar.progress = progress
    }
}

@BindingAdapter("loseFocusWhen")
fun loseFocusWhen(view: EditText, condition: Boolean) {
    if (condition) view.clearFocus()
}
