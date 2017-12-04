package de.dabotz.couchpotato.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.android.gms.location.DetectedActivity
import java.util.*

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
}