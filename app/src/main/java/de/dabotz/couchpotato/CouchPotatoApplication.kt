package de.dabotz.couchpotato

import android.app.Application
import android.app.PendingIntent
import de.dabotz.couchpotato.db.CouchPotatoDatabase
import android.arch.persistence.room.Room
import android.content.Intent
import com.github.salomonbrys.kodein.*
import com.google.android.gms.location.ActivityRecognition


/**
 * Created by Botz on 01.12.17.
 */
class CouchPotatoApplication: Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        bind<CouchPotatoDatabase>() with eagerSingleton {
            Room.databaseBuilder(
                            this@CouchPotatoApplication,
                            CouchPotatoDatabase::class.java,
                            "database-name")
                    .build()
        }

        bind<ActivityRepository>() with eagerSingleton {
            val database: CouchPotatoDatabase = instance()
            ActivityRepository(database.activityDao())
        }
    }

    override fun onCreate() {
        super.onCreate()
        val intent = Intent(this, ActivityRecognitionIntentService::class.java)
        val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        ActivityRecognition.getClient(this).requestActivityUpdates(10000, pendingIntent)
    }
}