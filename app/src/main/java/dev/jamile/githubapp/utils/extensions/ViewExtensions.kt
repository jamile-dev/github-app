package dev.jamile.githubapp.utils.extensions

import android.os.SystemClock
import android.view.View

abstract class DebouncedClickListener @JvmOverloads constructor(
    private var defaultInterval: Int = 1000
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        performClick(v)
    }

    // This is the method to call on click, must implement at child class
    abstract fun performClick(v: View)
}

fun View.setDebouncedClickListener(action: (() -> Unit)? = null) {
    if (action != null) {
        val debounce = object : DebouncedClickListener(800) {
            override fun performClick(v: View) {
                action()
            }
        }
        setOnClickListener(debounce)
    } else {
        setOnClickListener(null)
    }
}