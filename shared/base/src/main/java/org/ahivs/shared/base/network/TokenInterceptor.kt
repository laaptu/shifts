package org.ahivs.shared.base.network

import okhttp3.Interceptor
import okhttp3.Response
import org.ahivs.shared.base.cache.TokenRepo
import javax.inject.Inject

class TokenInterceptor @Inject constructor(private val tokenRepository: TokenRepo) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        tokenRepository.getAuthenticatedHeader().forEach { (key, value) ->
            requestBuilder.addHeader(key, value)
        }
        return chain.proceed(requestBuilder.build())
    }

}