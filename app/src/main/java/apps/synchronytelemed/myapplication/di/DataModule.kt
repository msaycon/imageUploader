package apps.synchronytelemed.myapplication.di

import apps.synchronytelemed.myapplication.ProjectApplication
import apps.synchronytelemed.myapplication.repository.room.RoomData
import apps.synchronytelemed.myapplication.repository.room.RoomDataImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by msaycon on 08,Jan,2020
 */

@Module
class DataModule(private val application: ProjectApplication) {

    @Provides
    @Singleton
    fun provideRoomData(): RoomData = RoomDataImpl(application)
}