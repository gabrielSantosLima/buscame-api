package com.app.buscameapi.robots

import com.app.buscameapi.dto.ProductDto

interface IProductSearchRobot : IRobot{
    fun search(text:String, params : Map<String, Any>, page : Int) : List<ProductDto>
}