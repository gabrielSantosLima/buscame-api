package com.app.buscameapi.dto

data class ProductDto(val name : String,
                      val text : String,
                      val price : Double,
                      val image : ByteArray,
                      val description : String,
                      val url : String,
                      val brandName : String
)