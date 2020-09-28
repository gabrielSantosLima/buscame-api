package com.app.buscameapi.robots

import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.dto.ProductDto

class Orchestrator {

    private val VG_APIKEY = "AS488150202020sads515"
    private val PS_APIKEY = "AS488150202020sads515"

    private val imageRobot = ImageRobot(VG_APIKEY)
    private val productSearchRobot = ProductSearchRobot(PS_APIKEY)

    fun search(imageDto: ImageDto, params: Map<String, String?>) : List<ProductDto>{

        imageRobot.authenticate()
        val text = imageRobot.imageAnalyzer(imageDto)

        productSearchRobot.authenticate()

        return productSearchRobot.search(text, params)
    }

    fun searchByText(text: String, params: Map<String, String?>) : List<ProductDto>{

        productSearchRobot.authenticate()

        return productSearchRobot.search(text, params)
    }
}
