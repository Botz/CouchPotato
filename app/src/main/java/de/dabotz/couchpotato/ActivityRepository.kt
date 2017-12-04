package de.dabotz.couchpotato

import de.dabotz.couchpotato.db.Activity
import de.dabotz.couchpotato.db.ActivityDao
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Botz on 03.12.17.
 */
class ActivityRepository(val activityDao: ActivityDao) {

    fun insert(activity: Activity) = activityDao.getCurrentActivty()
            .observeOn(Schedulers.io())
            .doOnSuccess {
                it.current = it.type == activity.type
            }
            .doOnError {
                println("Error")
            }
            //Return currentActivity or update old one with end date and return both
            .flatMapObservable { currentActivity ->
                if (currentActivity.current) {

                    println("Recognized Current Activity")
                    Observable.fromIterable(listOf(currentActivity))
                } else {
                    println("Add new Activity ${activity.type}")
                    currentActivity.end = Date()
                    Observable.fromIterable(listOf(currentActivity, activity))
                }
            }
            .subscribe( {
                activityDao.insert(it)
            }, {
                activityDao.insert(activity)
            })

    fun getActivities() = activityDao.getActivities()

}