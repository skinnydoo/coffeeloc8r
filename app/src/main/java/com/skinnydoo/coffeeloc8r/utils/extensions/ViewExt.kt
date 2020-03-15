package com.skinnydoo.coffeeloc8r.utils.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout
import com.skinnydoo.coffeeloc8r.glide.GlideApp
import com.skinnydoo.coffeeloc8r.glide.roundedCorners
import com.skinnydoo.coffeeloc8r.utils.AppBarState
import com.skinnydoo.coffeeloc8r.utils.SingleClickListener
import kotlin.math.abs


/**
 * Hide the nav bar and status bar
 */
fun Window.hideSystemUI() {
    decorView.systemUiVisibility =
        (View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN)
}

fun Window.hideSystemUIIf(predicate: () -> Boolean) {
    if (predicate()) {
        decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}

fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

/**
 * Shows the system bars by removing all the flags
 * except for the ones that make the content appear under the system bars.
 */
fun Window.showSystemUI() {
    decorView.systemUiVisibility =
        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    getLayoutInflater().inflate(layoutRes, this, attachToRoot)


/**
 * Extension method to provide quicker access to the [LayoutInflater] from a [View].
 */
fun View.getLayoutInflater(): LayoutInflater = LayoutInflater.from(context)

fun View.onClick( fn: (v: View) -> Unit) {
    setOnClickListener { fn(it) }
}

/**
 * Click listener setter that prevents double click on the view it's set
 */
fun View.singleClick(l: (v: View) -> Unit) {
    setOnClickListener(SingleClickListener(l))
}

/**
 * Extension method to show a keyboard for View.
 */
fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

/**
 * Hide the keyboard and returns whether it worked
 */
fun View.hideKeyboard(): Boolean {
    return try {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
        false
    }
}

/**
 * Show the view  (visibility = View.VISIBLE)
 */
fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Show the view if [predicate] returns true
 * (visibility = View.VISIBLE)
 */
inline fun View.showIf(predicate: () -> Boolean): View {
    if (visibility != View.VISIBLE && predicate()) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Show the view if [predicate] returns true
 * (visibility = View.VISIBLE)
 * else hide the view (visibility = View.GONE)
 */
fun View.showOrRemoveIf(predicate: () -> Boolean): View {
    if (predicate()) show() else remove()
    return this
}

/**
 * Hide the view. (visibility = View.INVISIBLE)
 */
fun View.hide(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
    return this
}

/**
 * Hide the view if [predicate] returns true
 * (visibility = View.INVISIBLE)
 */
inline fun View.hideIf(predicate: () -> Boolean): View {
    if (visibility != View.INVISIBLE && predicate()) {
        visibility = View.INVISIBLE
    }
    return this
}

/**
 * Remove the view (visibility = View.GONE)
 */
fun View.remove(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

/**
 * Remove the view if [predicate] returns true
 * (visibility = View.GONE)
 */
inline fun View.removeIf(predicate: () -> Boolean): View {
    if (visibility != View.GONE && predicate()) {
        visibility = View.GONE
    }
    return this
}

/**
 * Toggle a view's visibility
 */
fun View.toggleVisibility(): View {
    visibility = if (visibility == View.VISIBLE) {
        View.GONE
    } else {
        View.VISIBLE
    }
    return this
}

fun ImageView.loadFromUrl(
    url: String?,
    circle: Boolean = false,
    fitCenter: Boolean = false,
    cornersRadius: Float = 0f,
    @DrawableRes placeholder: Int = 0
) {
    GlideApp.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .run {
            when (placeholder) {
                0 -> placeholder(ColorDrawable(Color.LTGRAY))
                else -> placeholder(placeholder)
            }
        }
        .run {
            when {
                circle -> circleCrop()
                cornersRadius != 0f -> roundedCorners(cornersRadius, fitCenter)
                fitCenter -> fitCenter()
                else -> centerCrop()
            }
        }.into(this)
}

inline fun ViewPropertyAnimator.setListener(
    crossinline animationStart: (Animator) -> Unit = {},
    crossinline animationRepeat: (Animator) -> Unit = {},
    crossinline animationCancel: (Animator) -> Unit = {},
    crossinline animationEnd: (Animator) -> Unit = {}
): ViewPropertyAnimator = setListener(object : AnimatorListenerAdapter() {
    override fun onAnimationStart(animation: Animator) {
        animationStart(animation)
    }

    override fun onAnimationRepeat(animation: Animator) {
        animationRepeat(animation)
    }

    override fun onAnimationCancel(animation: Animator) {
        animationCancel(animation)
    }

    override fun onAnimationEnd(animation: Animator) {
        animationEnd(animation)
    }
})

inline fun AppBarLayout.addOnOffsetChangedListener(
    crossinline stateChanged: (appBarLayout: AppBarLayout, state: AppBarState) -> Unit
) = addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
    private var currentState = AppBarState.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        when {
            verticalOffset == 0 -> {
                if (currentState != AppBarState.EXPANDED) {
                    stateChanged(appBarLayout, AppBarState.EXPANDED)
                }
                currentState = AppBarState.EXPANDED
            }
            abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                if (currentState != AppBarState.COLLAPSED) {
                    stateChanged(appBarLayout, AppBarState.COLLAPSED)
                }
                currentState = AppBarState.COLLAPSED
            }
            else -> {
                if (currentState != AppBarState.IDLE) {
                    stateChanged(appBarLayout, AppBarState.IDLE)
                }
                currentState = AppBarState.IDLE
            }
        }
    }
})

fun View.color(@ColorRes color: Int): Int {
    return ContextCompat.getColor(context, color)
}

fun View.dimens(@DimenRes dim: Int): Float {
    return resources.getDimension(dim)
}

fun Context.drawable(@DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(this, res)
}
