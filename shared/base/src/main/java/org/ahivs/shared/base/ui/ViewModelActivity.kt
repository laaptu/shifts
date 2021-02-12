package org.ahivs.shared.base.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel
import dagger.android.AndroidInjection
import org.ahivs.shared.base.di.viewmodel.ViewModelProviderFactory
import org.ahivs.shared.base.ui.permissions.PermissionActivity
import javax.inject.Inject

abstract class ViewModelActivity<T : ViewModel> : PermissionActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    abstract val viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }
}