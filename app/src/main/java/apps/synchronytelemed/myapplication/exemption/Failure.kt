package apps.synchronytelemed.myapplication.exemption

/**
 * Created by msaycon on 01,Oct,2019
 */

sealed class Failure {
    object Exception : Failure()

    private lateinit var message: String

    open fun message(message: String) {
        this.message = message
    }

    val getMessage: String get() = this.message
}