package com.app.buscameapi.robots

import com.app.buscameapi.dto.ImageDto
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages

interface IImageRobot: IRobot {

    fun imageAnalyzer(image : ImageDto) : ClassifiedImages?
}