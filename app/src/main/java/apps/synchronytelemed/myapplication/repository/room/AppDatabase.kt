package apps.synchronytelemed.myapplication.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import apps.synchronytelemed.myapplication.repository.room.dao.DatabaseDao
import apps.synchronytelemed.myapplication.repository.room.entities.LocalImageFile

/**
 * Created by msaycon on 07,Jan,2020
 */
@Database(
    entities = [LocalImageFile::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localDatabaseDao(): DatabaseDao
}