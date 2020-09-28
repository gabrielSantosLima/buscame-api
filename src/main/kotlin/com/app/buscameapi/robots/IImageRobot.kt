package com.app.buscameapi.robots

import com.app.buscameapi.dto.ImageDto
import java.io.File

interface IImageRobot: IRobot {

    fun imageAnalyzer(image : ImageDto) : String
}