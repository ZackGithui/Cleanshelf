package com.example.cleanshelf.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ProductEntity::class,BookMark::class],
    version = 4,
    exportSchema = false)
abstract class ProductsDatabaseDatabase : RoomDatabase(){
    abstract fun productDao() : ProductDao
    abstract fun bookMarkDao(): BookMarkDao
}