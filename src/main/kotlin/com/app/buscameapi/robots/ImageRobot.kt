package com.app.buscameapi.robots

import com.app.buscameapi.dto.ImageDto
import java.io.File

class ImageRobot(override val API_KEY: String) : IImageRobot {

    override fun authenticate() {
        TODO("Implementar autenticação")
    }

    override fun imageAnalyzer(image: ImageDto) : String{
        TODO("Implementar analizador de imagem")

        return "computer"
    }
}