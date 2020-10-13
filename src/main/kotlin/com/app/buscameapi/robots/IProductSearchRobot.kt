package com.app.buscameapi.robots

import com.app.buscameapi.dto.Search

interface IProductSearchRobot : IRobot{
    val CS_CONTEXTKEY: String

    fun search(text:String, params : Map<String, String?>) : Search
}