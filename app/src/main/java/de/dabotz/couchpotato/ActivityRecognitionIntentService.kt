package de.dabotz.couchpotato

import android.content.Intent
import com.github.salomonbrys.kodein.android.KodeinIntentService
import com.github.salomonbrys.kodein.instance
import com.google.android.gms.location.ActivityRecognitionResult
import de.dabotz.couchpotato.db.Activity
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Botz on 24.11.17.
 */
class ActivityRecognitionIntentService: KodeinIntentService("ActivityRecognitionIntentService") {

    private val activityRepository:ActivityRepository by injector.instance()
    private val disposableBag: CompositeDisposable = CompositeDisposable()

    override fun onHandleIntent(intent: Intent) {
        val result = ActivityRecognitionResult.extractResult(intent)
        val activity = Activity.from(result.mostProbableActivity)
        val disposeable = activityRepository.insert(activity)
        disposableBag.add(disposeable)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        disposableBag.clear()
        return super.onUnbind(intent)
    }
}