package org.ahivs.shifts

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.ahivs.features.shifts.list.ui.ShiftsListActivity

class FakeSplashActivity : AppCompatActivity() {

    companion object {
        private const val DELAY = 300L
    }

    private val navigateRunnable = Runnable { startShiftsListActivity() }
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(navigateRunnable, DELAY)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(navigateRunnable)
    }

    private fun startShiftsListActivity() {
        startActivity(Intent(this, ShiftsListActivity::class.java))
        finish()
    }
}