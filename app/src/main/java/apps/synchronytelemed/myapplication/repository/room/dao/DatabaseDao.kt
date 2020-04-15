package apps.synchronytelemed.myapplication.repository.room.dao

import androidx.room.*
import apps.synchronytelemed.myapplication.repository.room.entities.LocalImageFile
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by msaycon on 07,Jan,2020
 */
@Dao
interface DatabaseDao {

    @Query("SELECT * FROM image_files")
    fun getImageFiles(): Flowable<List<LocalImageFile>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImageFile(localImageFile: LocalImageFile): Completable

    @Delete
    fun deleteImageFile(localImageFile: LocalImageFile): Completable
}