package org.ahivs.shared.base.network

import okhttp3.logging.HttpLoggingInterceptor
import org.ahivs.shared.base.BuildConfig
import javax.inject.Inject

class NetworkConfig @Inject constructor() {
    var apiBaseUrl = Urls.BASE
    val httpLoggingLevel: String by lazy {
        return@lazy if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY.name
        else
            HttpLoggingInterceptor.Level.NONE.name
    }
    var connectionTimeOutInSec = 30L
    var readTimeOutInSec = 30L
    var writeTimeOutInSec = 30L
}

object Urls {
    const val BASE = "https://apjoqdqpi3.execute-api.us-west-2.amazonaws.com/dmc"
    const val SHIFT_START = "/shift/start"
    const val SHIFT_END = "/shift/end"
    const val SHIFT_LIST = "/shifts"
}