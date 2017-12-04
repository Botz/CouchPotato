package de.dabotz.couchpotato.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import io.reactivex.Single

/**
 * Created by Botz on 02.12.17.
 */
@Dao
interface ActivityDao {

    @Query("SELECT * FROM activity WHERE current = 1 limit 1 ")
    fun getCurrentActivty() : Single<Activity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(activity: Activity)

    @Update
    fun update(activty: Activity)

    @Query("SELECT * FROM activity ORDER BY start DESC")
    fun getActivities(): LiveData<List<Activity>>
}