package org.ahivs.shifts.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.ahivs.features.shifts.actions.ShiftActionViewModel
import org.ahivs.features.shifts.list.ui.ShiftsListViewModel
import org.ahivs.shared.base.di.ComponentScope
import org.ahivs.shared.base.di.viewmodel.ViewModelKey

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ShiftsListViewModel::class)
    abstract fun provideShiftsListViewModel(shiftsListViewModel: ShiftsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShiftActionViewModel::class)
    abstract fun provideShiftActionViewModel(shiftActionViewModel: ShiftActionViewModel): ViewModel
}