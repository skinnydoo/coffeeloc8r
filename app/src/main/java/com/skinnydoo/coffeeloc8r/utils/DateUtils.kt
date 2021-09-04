package com.skinnydoo.coffeeloc8r.utils

import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

object DateUtils {

  @JvmStatic
  fun dayOfWeekAsText(day: Int): String {
    return DayOfWeek.of(day).getDisplayName(TextStyle.FULL, Locale.getDefault())
  }
}
