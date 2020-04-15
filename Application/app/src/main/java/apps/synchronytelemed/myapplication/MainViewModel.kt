package apps.synchronytelemed.myapplication

import androidx.lifecycle.MutableLiveData
import apps.synchronytelemed.myapplication.base.BaseViewModel
import apps.synchronytelemed.myapplication.repository.data.ImageFile
import apps.synchronytelemed.myapplication.repository.data.toImageFile
import apps.synchronytelemed.myapplication.repository.data.toLocalImageFile
import apps.synchronytelemed.myapplication.repository.room.RoomData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by msaycon on 15,Apr,2020
 */
class MainViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var roomData: RoomData

    var imageList: MutableLiveData<ArrayList<ImageFile>> = MutableLiveData()

    private var addedImage = ArrayList<ImageFile>()

    private val disposable = CompositeDisposable()

    fun getImageFiles() {
        disposable.add(roomData.getDao().getImageFiles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { localImageList ->
                Flowable.fromIterable(localImageList)
                    .map { it.toImageFile }
                    .doOnComplete {
                        imageList.value =
                            ArrayList(addedImage.sortedWith(compareByDescending { it.time }))
                        addedImage.clear()
                    }
                    .subscribe {
                        if (!addedImage.contains(it)) {
                            addedImage.add(it)
                        }
                    }
            }
        )
    }

    fun saveImageFile(imageFile: ImageFile) {
        disposable.add(
            roomData.getDao().insertImageFile(imageFile.toLocalImageFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun deleteImageFile(imageFile: ImageFile) {
        disposable.add(
            roomData.getDao().deleteImageFile(imageFile.toLocalImageFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }
}