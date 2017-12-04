package de.dabotz.couchpotato.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

/**
 * Created by Botz on 02.12.17.
 */
@Database(entities = arrayOf(Activity::class), version = 1)
@TypeConverters(Converters::class)
abstract class CouchPotatoDatabase: RoomDatabase() {
    abstract fun activityDao() : ActivityDao
}