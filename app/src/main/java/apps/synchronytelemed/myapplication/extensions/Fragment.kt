package apps.synchronytelemed.myapplication.extensions

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import apps.synchronytelemed.myapplication.base.BaseFragment

/**
 * Created by msaycon on 01,Oct,2019
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

inline fun <reified T : ViewModel> Fragment.viewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val vm = ViewModelProvider(this, factory)[T::class.java]
    vm.body()
    return vm
}

fun BaseFragment.close() = activity?.supportFragmentManager?.popBackStack()

val BaseFragment.viewContainer: View
    get() {
        return view!!
    }

val BaseFragment.appContext: Context get() = activity?.applicationContext!!