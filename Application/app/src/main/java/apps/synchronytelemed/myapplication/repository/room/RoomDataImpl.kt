package apps.synchronytelemed.myapplication.repository.room

import androidx.room.Room
import apps.synchronytelemed.myapplication.ProjectApplication
import apps.synchronytelemed.myapplication.repository.room.dao.DatabaseDao
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by msaycon on 08,Jan,2020
 */
class RoomDataImpl
@Inject constructor(private val application: ProjectApplication) : RoomData {

    lateinit var appDatabase: AppDatabase

    override fun initializeDatabase() {
        appDatabase = Room.databaseBuilder(
            application,
            AppDatabase::class.java, "imagefiles.db"
        ).build()
    }

    override fun getDatabaseInstance(): AppDatabase = appDatabase

    override fun getDao(): DatabaseDao = appDatabase.localDatabaseDao()

    override fun clearAllTables(): Completable {
        return Completable.fromAction {
            getDatabaseInstance().clearAllTables()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}