package de.dabotz.couchpotato.myDay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.android.KodeinSupportFragment
import com.github.salomonbrys.kodein.instance
import com.google.android.gms.location.DetectedActivity
import de.dabotz.couchpotato.ActivityRepository
import de.dabotz.couchpotato.R
import io.reactivex.disposables.CompositeDisposable
import khronos.beginningOfDay
import kotlinx.android.synthetic.main.fragment_myday.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Botz on 08.12.17.
 */
class MyDayFragment: KodeinSupportFragment() {

    val repository: ActivityRepository by injector.instance()

    val disposableBag = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_myday, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        circleProgress.max = TimeUnit.HOURS.toMillis(24.toLong())
    }

    override fun onResume() {
        super.onResume()
        circleProgress.progress = 0
        var disposable = repository.getActivitiesForToday(DetectedActivity.STILL)
                .subscribe({
                    circleProgress.progress += it.durationToday.toInt()
                },{

                })

        disposableBag.add(disposable)
    }

    override fun onStop() {
        super.onStop()
        disposableBag.clear()
    }

}