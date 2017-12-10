package de.dabotz.couchpotato

import android.os.Bundle
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import de.dabotz.couchpotato.list.ActivityListFragment
import de.dabotz.couchpotato.myDay.MyDayFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : KodeinAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            var fragment = when(item.itemId) {
                R.id.action_myDay -> MyDayFragment()
                R.id.action_activityList -> ActivityListFragment()
                else -> MyDayFragment()
            }

            supportFragmentManager.beginTransaction()
                    .replace(R.id.mainActivity_container, fragment)
                    .commit()
            true
        }

        bottom_navigation.selectedItemId = R.id.action_myDay
    }

}
