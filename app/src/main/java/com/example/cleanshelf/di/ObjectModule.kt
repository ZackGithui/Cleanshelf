package com.example.cleanshelf.di

import com.example.cleanshelf.data.remote.CartApiService
import com.example.cleanshelf.data.remote.Cleanshelf
import com.example.cleanshelf.data.remote.MpesaServices
import com.example.cleanshelf.data.remote.OrderApiService

import com.example.cleanshelf.util.BASE_URL
import com.example.cleanshelf.util.MPESAAPI_BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ObjectModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    private val BaseUrl = BASE_URL.trim().replace(" ", "")
    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    }

    @Provides
    @Singleton
    fun provideCleanShelfApi(retrofit: Retrofit): Cleanshelf {
        return retrofit.create(Cleanshelf::class.java)
    }

    val sanitizedBaseUrl = MPESAAPI_BASE_URL.trim().replace(" ", "")


    @Provides
    @Singleton
    fun providesMpesaAPI(moshi: Moshi): MpesaServices{
        return  Retrofit.Builder()
            .baseUrl(sanitizedBaseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MpesaServices::class.java)
    }
    @Singleton
    @Provides
    fun provideCartApiService(retrofit: Retrofit): CartApiService {
        return retrofit.create(CartApiService::class.java)
    }

    @Singleton
    @Provides
    fun createOrderService(retrofit: Retrofit): OrderApiService {
        return retrofit.create(OrderApiService::class.java)
    }


}

