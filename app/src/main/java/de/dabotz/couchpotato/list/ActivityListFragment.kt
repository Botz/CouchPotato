package de.dabotz.couchpotato.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.android.KodeinSupportFragment
import com.github.salomonbrys.kodein.instance
import de.dabotz.couchpotato.ActivityAdapter
import de.dabotz.couchpotato.ActivityRepository
import de.dabotz.couchpotato.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_activitylist.*

/**
 * Created by Botz on 08.12.17.
 */
class ActivityListFragment: KodeinSupportFragment() {
    val repository: ActivityRepository by injector.instance()

    val disposableBag = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_activitylist, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val adapter = ActivityAdapter()
        activityList_recyclerView.adapter = adapter

        val disposable = repository.getActivities()
                .subscribe({
                    adapter.items.clear()
                    adapter.items.addAll(it)
                    adapter.notifyDataSetChanged()
                }, {

                })

        disposableBag.add(disposable)
    }

    override fun onStop() {
        super.onStop()
        disposableBag.clear()
    }
}