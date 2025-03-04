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
