package de.dabotz.couchpotato

import com.google.android.gms.location.DetectedActivity
import de.dabotz.couchpotato.db.Activity
import de.dabotz.couchpotato.db.ActivityDao
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Botz on 03.12.17.
 */
class ActivityRepository(val activityDao: ActivityDao) {

    fun insert(activity: Activity) = activityDao.getCurrentActivty()
            .observeOn(Schedulers.io())
            .filter {
                it.type != activity.type && when(activity.type) {
                    DetectedActivity.UNKNOWN, DetectedActivity.TILTING -> false
                    else -> true
                }
            }
            .subscribe( {
                println("insert new activity and old one for update")
                it.current = false
                it.end = Date()
                activityDao.insert(activity, it)
            }, {
                activityDao.insert(activity)
            })

    fun getActivities() = activityDao.getActivities()

}