package com.example.cleanshelf.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ProductEntity")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val quantity: Int,
    val price: Int

)


@Entity(tableName = "BookMark")
data class BookMark(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val price: Int,
    val unit : String,
    val category: String,
    val number: Int
)
