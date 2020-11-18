package com.app.buscameapi.robots

import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.dto.ClassifierDto

interface IImageRobot: IRobot, IAuthenticateRobot{

    fun imageAnalyzer(image : ImageDto) : List<ClassifierDto>
}