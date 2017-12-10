package de.dabotz.couchpotato

import com.google.android.gms.location.DetectedActivity
import de.dabotz.couchpotato.db.Activity
import de.dabotz.couchpotato.db.ActivityDao
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import khronos.beginningOfDay
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

    fun getActivitiesForToday(type: Int) = activityDao.getActivitiesByType(type)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .flatMap {
                Flowable.fromIterable(it)
            }
            .filter {
                it.current || it.end?.let{ it >= Date().beginningOfDay } ?: false
            }

    fun getActivities() = activityDao.getActivities()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

}