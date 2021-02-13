package org.ahivs.shared.base.ui

import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity : AppCompatActivity() {
    protected fun <T : ViewDataBinding> binding(@LayoutRes layoutId: Int): Lazy<T> =
        lazy {
            DataBindingUtil.setContentView<T>(this, layoutId)
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return true
    }
}
