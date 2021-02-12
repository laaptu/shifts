package org.ahivs.shifts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.ahivs.features.shifts.actions.ShiftActionActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, ShiftActionActivity::class.java))
    }
}