package com.skinnydoo.coffeeloc8r.utils.extensions

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.math.roundToInt

val <T> T.exhaustive: T
    get() = this

val Float.toPx: Float get() = (this * Resources.getSystem().displayMetrics.density)

val Int.toPx: Int get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

val Float.sp2Px: Float get() = this * Resources.getSystem().displayMetrics.scaledDensity

val Int.sp2Px: Int get() = (this * Resources.getSystem().displayMetrics.scaledDensity).roundToInt()

fun Double.format(digits: Int): String = java.lang.String.format("%.${digits}f", this)


/**
 * Extension for applying SharedPreferences
 */
inline fun SharedPreferences.applyMe(fn: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    edit().fn().apply()
}

inline fun <reified T> String.toObject(): T = Gson().fromJson(this, T::class.java)

inline fun <reified T> String.fromJson(): T = Gson().fromJson(this, object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.toObject(map: Map<*, *>): T =
    fromJson(toJsonTree(map), T::class.java)

/**
 * Joins 2 collections by a foreign key predicate
 */
inline fun <T : Any, U : Any> List<T>.joinBy(
    collection: List<U>,
    crossinline filter: (Pair<T, U>) -> Boolean
): List<Pair<T, List<U>>> = map { t ->
    val filtered = collection.filter { filter(Pair(t, it)) }
    Pair(t, filtered)
}

inline fun <T : Any, U : Any> Sequence<T>.joinBy(
    collection: List<U>,
    crossinline filter: (Pair<T, U>) -> Boolean
): Sequence<Pair<T, List<U>>> = map { t ->
    val filtered = collection.filter { filter(Pair(t, it)) }
    Pair(t, filtered)
}

inline fun <reified T : Enum<T>> Intent.putEnumExtra(name: String, enum: T): Intent =
    putExtra(name, enum.ordinal)

/*
* Retrieve extended data from the intent.
* @param name: the name of the desired item
* @return: the value of an item that previously added with putEnumExtra() or null if no Enum value was found.
* */
inline fun <reified T : Enum<T>> Intent.getEnumExtra(name: String): T? =
    getIntExtra(name, -1).takeUnless { it == -1 }?.let { T::class.java.enumConstants?.get(it) }

/** Compatibility removeIf since it was added in API 24 */
fun <T> MutableCollection<T>.compatRemoveIf(predicate: (T) -> Boolean): Boolean {
    val it = iterator()
    var removed = false
    while (it.hasNext()) {
        if (predicate(it.next())) {
            it.remove()
            removed = true
        }
    }
    return removed
}
