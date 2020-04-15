package apps.synchronytelemed.myapplication.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import apps.synchronytelemed.myapplication.exemption.Failure

/**
 * Created by msaycon on 01,Oct,2019
 */
abstract class BaseViewModel : ViewModel() {

    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }
}