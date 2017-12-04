package de.dabotz.couchpotato.db

import android.arch.persistence.room.TypeConverter
import java.util.*


/**
 * Created by Botz on 02.12.17.
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}