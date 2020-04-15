package apps.synchronytelemed.myapplication.repository.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by msaycon on 15,Apr,2020
 */
@Entity(tableName = "image_files")
data class LocalImageFile(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "time") val time: Long,
    @ColumnInfo(name = "filePath") val filePath: String,
    @ColumnInfo(name = "caption") val caption: String?
)