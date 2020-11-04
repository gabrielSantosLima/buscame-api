package com.app.buscameapi.robots

import com.app.buscameapi.dto.ProductDto

interface IProductSearchRobot : IRobot{
    val CS_CONTEXTKEY: String

    fun search(text:String, params : Map<String, String?>) : List<ProductDto>
}