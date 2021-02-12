package org.ahivs.shared.base.utils

import android.os.Build
import org.ahivs.shared.base.BuildConfig
import org.ahivs.shared.base.di.AppScope
import timber.log.Timber
import javax.inject.Inject

@AppScope
class Logger @Inject constructor() {
    init {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    fun debug(tag: String, msg: String) = Timber.tag(tag).d(msg)

    fun error(tag: String, msg: String) = Timber.tag(tag).e(msg)

    fun warn(tag: String, msg: String) = Timber.tag(tag).w(msg)
}