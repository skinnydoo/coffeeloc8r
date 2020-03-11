package com.skinnydoo.coffeeloc8r.utils

import org.threeten.bp.DayOfWeek
import org.threeten.bp.format.TextStyle
import java.util.*

object DateUtils {

    @JvmStatic
    fun dayOfWeekAsText(day: Int): String {
        return DayOfWeek.of(day).getDisplayName(TextStyle.FULL, Locale.getDefault())
    }
}
