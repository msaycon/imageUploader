package apps.synchronytelemed.myapplication.di

import apps.synchronytelemed.myapplication.MainActivity
import apps.synchronytelemed.myapplication.MainFragment
import apps.synchronytelemed.myapplication.ProjectApplication
import apps.synchronytelemed.myapplication.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by msaycon on 01,Oct,2019
 */

@Singleton
@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(application: ProjectApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
}
