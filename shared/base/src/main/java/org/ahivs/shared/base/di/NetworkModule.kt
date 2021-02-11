package org.ahivs.shared.base.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.ahivs.shared.base.network.NetworkConfig
import org.ahivs.shared.base.network.TokenInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(
        networkConfig: NetworkConfig,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(networkConfig.connectionTimeOutInSec, TimeUnit.SECONDS)
            .readTimeout(networkConfig.readTimeOutInSec, TimeUnit.SECONDS)
            .writeTimeout(networkConfig.writeTimeOutInSec, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideLoggingInterceptor(networkConfig: NetworkConfig): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.valueOf(networkConfig.httpLoggingLevel)
        }

    @Provides
    @Singleton
    fun provideRetrofit(
        okhttpClient: OkHttpClient,
        networkConfig: NetworkConfig
    ): Retrofit =
        Retrofit.Builder()
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(networkConfig.apiBaseUrl)
            .build()
}