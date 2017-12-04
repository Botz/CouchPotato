package de.dabotz.couchpotato

import android.arch.persistence.room.EmptyResultSetException
import com.google.android.gms.location.DetectedActivity
import de.dabotz.couchpotato.db.Activity
import de.dabotz.couchpotato.db.ActivityDao
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import io.reactivex.schedulers.Schedulers
import io.reactivex.Scheduler
import io.reactivex.plugins.RxJavaPlugins




@RunWith(MockitoJUnitRunner::class)
class ActivityRepositoryTest {

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `test insert inserts given Activity if DB is empty`() {
        var activityDao: ActivityDao = mock(ActivityDao::class.java)
        `when`(activityDao.getCurrentActivty()).thenReturn(
                Single.create {
                    it.onError(EmptyResultSetException("SELECT * FROM activity WHERE current = 1 LIMIT 1 returns empty resultset"))
                }
        )

        val repository = ActivityRepository(activityDao)
        val activity = Activity.from(DetectedActivity(DetectedActivity.STILL, 100))

        repository.insert(activity)

        verify(activityDao).insert(activity)
    }


    @Test
    fun `test insert inserts given Activity if it isn't the same as the current Activity`() {
        var activityDao: ActivityDao = mock(ActivityDao::class.java)
        val currentActivity = Activity.from(DetectedActivity(DetectedActivity.STILL, 100))
        `when`(activityDao.getCurrentActivty()).thenReturn(
                Single.create {
                    it.onSuccess(currentActivity)
                }
        )

        val repository = ActivityRepository(activityDao)
        val newActivity = Activity.from(DetectedActivity(DetectedActivity.ON_FOOT, 100))

        repository.insert(newActivity)

        verify(activityDao).insert(newActivity, currentActivity)
    }

    @Test
    fun `test insert doesn't insert Activity if is has same type as current Activity`() {
        var activityDao: ActivityDao = mock(ActivityDao::class.java)
        val currentActivity = Activity.from(DetectedActivity(DetectedActivity.STILL, 100))
        `when`(activityDao.getCurrentActivty()).thenReturn(
                Single.create {
                    it.onSuccess(currentActivity)
                }
        )

        val repository = ActivityRepository(activityDao)
        val newActivity = Activity.from(DetectedActivity(DetectedActivity.STILL, 100))

        repository.insert(newActivity)

        verify(activityDao, never()).insert(newActivity, currentActivity)
        verify(activityDao, never()).insert(newActivity)
    }

    @Test
    fun `test insert doesn't insert Activity if it is from type TILTING`() {
        var activityDao: ActivityDao = mock(ActivityDao::class.java)
        val currentActivity = Activity.from(DetectedActivity(DetectedActivity.STILL, 100))
        `when`(activityDao.getCurrentActivty()).thenReturn(
                Single.create {
                    it.onSuccess(currentActivity)
                }
        )

        val repository = ActivityRepository(activityDao)
        val newActivity = Activity.from(DetectedActivity(DetectedActivity.TILTING, 100))

        repository.insert(newActivity)

        verify(activityDao, never()).insert(newActivity, currentActivity)
        verify(activityDao, never()).insert(newActivity)
    }

    @Test
    fun `test insert doesn't insert Activity if it is from type UNKNOWN`() {
        var activityDao: ActivityDao = mock(ActivityDao::class.java)
        val currentActivity = Activity.from(DetectedActivity(DetectedActivity.STILL, 100))
        `when`(activityDao.getCurrentActivty()).thenReturn(
                Single.create {
                    it.onSuccess(currentActivity)
                }
        )

        val repository = ActivityRepository(activityDao)
        val newActivity = Activity.from(DetectedActivity(DetectedActivity.UNKNOWN, 100))

        repository.insert(newActivity)

        verify(activityDao, never()).insert(newActivity, currentActivity)
        verify(activityDao, never()).insert(newActivity)
    }

}