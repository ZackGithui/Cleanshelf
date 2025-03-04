package com.example.cleanshelf.domain.cart

data class CartItem (
    val id : Int,
    val name : String,
    val image : String,
    val description : String,
    val quantity : Int,
    val price : Int
)