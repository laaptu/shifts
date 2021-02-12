package org.ahivs.shifts.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import org.ahivs.shared.base.di.AppScope
import org.ahivs.shared.base.di.BaseComponent
import org.ahivs.shifts.MainApplication
import org.ahivs.shifts.di.modules.ActivityModule
import org.ahivs.shifts.di.modules.ApiModule
import org.ahivs.shifts.di.modules.ViewModelModule

@Component(
    modules = [AndroidInjectionModule::class, ActivityModule::class, ViewModelModule::class, ApiModule::class],
    dependencies = [BaseComponent::class]
)
@AppScope
interface AppComponent {
    fun injectOnApp(mainApplication: MainApplication)

    @Component.Builder
    interface Builder {
        fun addBaseComponent(baseComponent: BaseComponent): Builder

        @BindsInstance
        fun addContext(context: Context): Builder
        fun build(): AppComponent
    }
}