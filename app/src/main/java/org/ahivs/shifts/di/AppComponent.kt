package org.ahivs.shifts.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import org.ahivs.shared.base.di.AppScope
import org.ahivs.shared.base.di.BaseComponent
import org.ahivs.shifts.MainApplication
import org.ahivs.shifts.di.modules.ApiModule

@Component(
    modules = [AndroidInjectionModule::class, ApiModule::class],
    dependencies = [BaseComponent::class]
)
@AppScope
interface AppComponent {
    fun injectOnApp(mainApplication: MainApplication)

    @Component.Builder
    interface Builder {
        fun addBaseComponent(baseComponent: BaseComponent): Builder
        fun build(): AppComponent
    }
}