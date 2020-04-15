package apps.synchronytelemed.myapplication.repository.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by msaycon on 15,Apr,2020
 */
@Parcelize
data class ImageFile(val id: String, val time: Long, val filePath: String, val caption: String?) :
    Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageFile

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}