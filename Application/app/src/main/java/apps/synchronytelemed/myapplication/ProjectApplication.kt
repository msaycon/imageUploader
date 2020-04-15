package apps.synchronytelemed.myapplication

import android.app.Application
import apps.synchronytelemed.myapplication.di.AppComponent
import apps.synchronytelemed.myapplication.di.DaggerAppComponent
import apps.synchronytelemed.myapplication.di.DataModule
import apps.synchronytelemed.myapplication.extensions.DebugTree
import apps.synchronytelemed.myapplication.repository.room.RoomData
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by msaycon on 15,Apr,2020
 */

class ProjectApplication : Application() {

    @Inject
    lateinit var roomData: RoomData

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerAppComponent
            .builder()
            .dataModule(DataModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        Timber.plant(DebugTree())

        roomData.initializeDatabase()
    }
}