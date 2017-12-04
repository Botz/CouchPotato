package de.dabotz.couchpotato

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.location.DetectedActivity
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by Botz on 03.12.17.
 */
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:activity_type")
    fun setVisiblity(view: TextView, activity_type: Int) {
        view.text = when(activity_type) {
            DetectedActivity.IN_VEHICLE -> "Autofahren"
            DetectedActivity.STILL -> "Couch Potato"
            DetectedActivity.ON_BICYCLE -> "Fahrradfahren"
            DetectedActivity.RUNNING -> "Laufen"
            DetectedActivity.ON_FOOT -> "Spazierengehen oder Laufen"
            DetectedActivity.WALKING -> "Sapzierengehen"
            else -> "Unbekannt"
        }
    }

    @JvmStatic
    @BindingAdapter(value= arrayOf("android:start_date"))
    fun setDate(view:TextView, start_date:Date) {
        view.text = SimpleDateFormat("dd.MM.yyyy  HH:mm:ss", Locale.GERMANY).format(start_date)
    }
}