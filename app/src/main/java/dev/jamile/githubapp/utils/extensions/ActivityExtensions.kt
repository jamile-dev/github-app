package dev.jamile.githubapp.utils.extensions

import android.app.Activity
import android.content.Intent

fun Intent.clearBackStack(): Intent {
    this.addFlags(
        Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_NEW_TASK)
    return this
}

inline fun <reified T : Activity> Activity.clearBackStackAndStartActivity() {
    val intent = Intent(this, T::class.java)
    intent.clearBackStack()
    finishAffinity()
    startActivity(intent)
}