package apps.synchronytelemed.myapplication.helpers

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by msaycon on 15,Apr,2020
 */
val Long.toDayOfWeek: String
    get() {
        return when {
            DateUtils.isToday(this) -> {
                "Today"
            }
            DateUtils.isToday(this + DateUtils.DAY_IN_MILLIS) -> {
                "Yesterday"
            }
            else -> {
                Date(this).standardFormat
            }
        }
    }

val Date.standardFormat: String
    get() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return dateFormat.format(this)
    }

val Long.standardFormatFull: String
    get() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm", Locale.getDefault())
        return dateFormat.format(Date(this))
    }