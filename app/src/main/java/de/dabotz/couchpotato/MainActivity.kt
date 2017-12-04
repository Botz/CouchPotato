package de.dabotz.couchpotato

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : KodeinAppCompatActivity() {

    val repository:ActivityRepository by injector.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ActivityAdapter()

        activityRecyclerView.adapter = adapter

        repository.getActivities().observe(this, Observer {
            println("LIVEDATA: ${it?.count()}")
            adapter.items.clear()
            adapter.items.addAll(it ?: listOf())
            adapter.notifyDataSetChanged()
        })
    }
}
