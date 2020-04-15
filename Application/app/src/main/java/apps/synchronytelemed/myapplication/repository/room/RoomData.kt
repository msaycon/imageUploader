package apps.synchronytelemed.myapplication.repository.room

import apps.synchronytelemed.myapplication.repository.room.dao.DatabaseDao
import io.reactivex.Completable

/**
 * Created by msaycon on 08,Jan,2020
 */
interface RoomData {
    fun initializeDatabase()

    fun getDatabaseInstance(): AppDatabase

    fun getDao(): DatabaseDao

    fun clearAllTables(): Completable
}