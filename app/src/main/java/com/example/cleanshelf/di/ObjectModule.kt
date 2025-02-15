package com.example.cleanshelf.di

import com.example.cleanshelf.data.remote.Cleanshelf
import com.example.cleanshelf.data.repository.ProductsRepositoryImpl
import com.example.cleanshelf.domain.repository.ProductsRepository
import com.example.cleanshelf.util.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(Singleton::class)
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




}