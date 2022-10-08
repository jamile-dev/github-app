package dev.jamile.githubapp.ui.splash

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import dev.jamile.githubapp.R
import dev.jamile.githubapp.ui.MainActivity
import dev.jamile.githubapp.utils.extensions.clearBackStackAndStartActivity
import dev.jamile.githubapp.utils.extensions.haveConnection
import kotlinx.android.synthetic.main.splash_screen_layout.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_layout)
        setupMotionLayout()
    }

    private fun returnHaveConnection(): Boolean {
        return this.haveConnection()
    }

    private fun setupMotionLayout() {
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                if (returnHaveConnection()) {
                    clearBackStackAndStartActivity<MainActivity>()
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.verify_connection),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })
    }
}

