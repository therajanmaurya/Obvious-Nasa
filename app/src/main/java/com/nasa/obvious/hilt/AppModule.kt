package com.nasa.obvious.hilt

import com.nasa.obvious.api.NasaService
import com.nasa.obvious.data.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://raw.githubusercontent.com"

    @Provides
    fun provideNasaService(): NasaService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(NasaService::class.java)
    }

    @Provides
    fun provideRepository(nasaService: NasaService): Repository {
        return Repository(nasaService)
    }
}
