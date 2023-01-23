package com.application.hmkcopy.di

import com.application.hmkcopy.service.RequestInterceptor
import com.application.hmkcopy.service.Service
import com.application.hmkcopy.service.UploadService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CONNECT_TIMEOUT = 15L
    private const val WRITE_TIMEOUT = 15L
    private const val READ_TIMEOUT = 15L
    private const val BASE_URL = "http://167.71.44.214:80/v1/"


    @Provides
    @Singleton
    fun provideOkHttpClient(
        requestInterceptor: RequestInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.MINUTES)
            readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
            retryOnConnectionFailure(true)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            addInterceptor(requestInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    }

    @Provides
    @Singleton
    fun provideUploadServiceAmazon(okHttpClient: OkHttpClient): UploadService {
        return Retrofit.Builder()
            .baseUrl("https://testingdeneme.s3.eu-central-1.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UploadService::class.java)
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): Service {
        return retrofit.create(Service::class.java)
    }
}