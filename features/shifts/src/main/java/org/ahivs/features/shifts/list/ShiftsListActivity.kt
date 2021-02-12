package org.ahivs.features.shifts.list

import android.os.Bundle
import androidx.activity.viewModels
import org.ahivs.features.shifts.R
import org.ahivs.shared.base.ui.ViewModelActivity

class ShiftsListActivity : ViewModelActivity<ShiftsListViewModel>() {
    override val viewModel: ShiftsListViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shifts_list)
        viewModel.fetchShifts()
    }
}