package org.ahivs.shifts

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.ahivs.features.shifts.list.ui.ShiftsListActivity
import java.text.SimpleDateFormat
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            startShiftsListActivity()
        }, 1000)
    }

    private fun startShiftsListActivity() {
        startActivity(Intent(this, ShiftsListActivity::class.java))
        finish()
    }
}