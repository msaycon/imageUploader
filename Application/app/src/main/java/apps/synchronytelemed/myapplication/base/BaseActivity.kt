package apps.synchronytelemed.myapplication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import apps.synchronytelemed.myapplication.R

/**
 * Created by msaycon on 01,Oct,2019
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(
            R.id.fragment_container
        ) as BaseFragment).onBackPressed()
    }

    abstract fun addFragment(savedInstanceState: Bundle?) : Any

    abstract fun fragment(): BaseFragment
}