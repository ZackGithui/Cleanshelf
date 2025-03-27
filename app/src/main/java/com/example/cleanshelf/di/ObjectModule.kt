package com.example.cleanshelf.di

import com.example.cleanshelf.data.remote.Cleanshelf
import com.example.cleanshelf.data.remote.MpesaServices

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

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    }

    @Provides
    @Singleton
    fun provideCleanShelfApi(retrofit: Retrofit): Cleanshelf {
        return retrofit.create(Cleanshelf::class.java)
    }

    @Provides
    @Singleton
    fun providesMpesaAPI(moshi: Moshi): MpesaServices{
        return  Retrofit.Builder()
            .baseUrl(MPESAAPI_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MpesaServices::class.java)
    }



}

