package apps.synchronytelemed.myapplication.repository.data

import apps.synchronytelemed.myapplication.repository.room.entities.LocalImageFile

/**
 * Created by msaycon on 15,Apr,2020
 */
val LocalImageFile.toImageFile: ImageFile
    get() {
        return ImageFile(
            id,
            time,
            filePath,
            caption
        )
    }

val ImageFile.toLocalImageFile: LocalImageFile
    get() {
        return LocalImageFile(
            id,
            time,
            filePath,
            caption
        )
    }