package de.dabotz.couchpotato.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.android.gms.location.DetectedActivity
import khronos.Dates
import khronos.beginningOfDay
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Botz on 02.12.17.
 */
@Entity
class Activity(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        var type: Int,
        var confidence: Int,
        var current:Boolean = false,
        var start: Date,
        var end: Date? = null
) {
    companion object {
        fun from(detectedActivity: DetectedActivity, current: Boolean = true) = Activity(
                    type = detectedActivity.type,
                    confidence = detectedActivity.confidence,
                    start = Date(),
                    current = current
            )
    }

    val duration: Long
        get() {
            val endDate = end ?: Date()
            val millis = endDate.time - start.time
            return millis
        }

    val durationToday: Long
        get() {
            val endDate = end ?: Date()
            if (start < Date().beginningOfDay) {
                return endDate.time - Date().beginningOfDay.time
            }
            return endDate.time - start.time
        }
}