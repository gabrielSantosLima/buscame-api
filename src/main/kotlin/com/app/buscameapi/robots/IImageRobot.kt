package com.app.buscameapi.robots

import com.app.buscameapi.dto.ImageDto

interface IImageRobot: IRobot {

    fun imageAnalyzer(file : ImageDto) : String
}