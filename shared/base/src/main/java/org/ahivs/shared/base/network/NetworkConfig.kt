package org.ahivs.shared.base.network

import okhttp3.logging.HttpLoggingInterceptor
import org.ahivs.shared.base.BuildConfig
import javax.inject.Inject

class NetworkConfig @Inject constructor() {
    var apiBaseUrl = Network.Urls.BASE
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


object Network {
    object Urls {
        const val BASE = "https://apjoqdqpi3.execute-api.us-west-2.amazonaws.com/dmc/"
        const val SHIFT_START = "shift/start"
        const val SHIFT_END = "shift/end"
        const val SHIFT_LIST = "shifts"
    }

    val headers: Map<String, String> = mapOf(
        "Content-Type" to "application/json",
        "Authorization" to "Deputy 117a211f76f0aa5fbc9b7143b793fdbaf7088c09"
    )
}
