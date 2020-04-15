package apps.synchronytelemed.myapplication.extensions

import android.util.Log
import timber.log.Timber

/**
 * Created by msaycon on 18,Nov,2019
 */
class DebugTree : Timber.DebugTree() {
    override fun log(priority: Int, callingClass: String?, message: String, t: Throwable?) {
        Log.println(priority, "MyApplication" + callingClass!!, message)
    }
}