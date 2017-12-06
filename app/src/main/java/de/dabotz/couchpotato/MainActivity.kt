package de.dabotz.couchpotato

import android.os.Bundle
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.instance
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : KodeinAppCompatActivity() {

    val repository:ActivityRepository by injector.instance()

    val disposableBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
        val adapter = ActivityAdapter()

        activityRecyclerView.adapter = adapter


        var disposable = repository.getActivities()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    adapter.items.clear()
                    adapter.items.addAll(it)
                    adapter.notifyDataSetChanged()
                }, {
                    println("FUUUUU")
                })

        disposableBag.add(disposable)
        */
    }

    override fun onStop() {
        super.onStop()
        disposableBag.clear()
    }
}
