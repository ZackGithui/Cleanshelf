package com.example.cleanshelf.di

import android.content.Context
import androidx.room.Room
import com.example.cleanshelf.data.local.BookMarkDao
import com.example.cleanshelf.data.local.ProductDao
import com.example.cleanshelf.data.local.ProductsDatabaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn (SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides

    fun provideDatabase(@ApplicationContext context: Context) : ProductsDatabaseDatabase{
        return Room.databaseBuilder(context,ProductsDatabaseDatabase::class.java,"Products")
            .build()
    }

    @Singleton
    @Provides
    fun provideProductDao(productsDatabaseDatabase: ProductsDatabaseDatabase) :ProductDao{
        return productsDatabaseDatabase.productDao()
    }


    @Singleton
    @Provides
    fun provideBookMarkDao(productsDatabaseDatabase: ProductsDatabaseDatabase) : BookMarkDao{
        return productsDatabaseDatabase.bookMarkDao()
    }


}