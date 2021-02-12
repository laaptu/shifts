package org.ahivs.shifts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.ahivs.features.shifts.list.ShiftsListActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, ShiftsListActivity::class.java))
    }
}