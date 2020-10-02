package com.app.buscameapi.robots

import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.dto.ProductDto
import com.app.buscameapi.util.Properties
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages
import org.springframework.stereotype.Service

@Service
class Orchestrator {

    private val credentials = Properties("src/main/resources/credentials.properties")
    private val values = Properties("src/main/resources/values.properties")

    private val VG_APIKEY = credentials?.getProperty("VG_APIKEY")
    private val CS_APIKEY = credentials?.getProperty("CS_APIKEY")
    private val URL_VG = values?.getProperty("VG_URL")
    private val URL_CS = values?.getProperty("CS_URL")

    private val imageRobot = ImageRobot(
            VG_APIKEY!!,
            URL_VG!!
    )
    private val productSearchRobot = ProductSearchRobot(CS_APIKEY!!, URL_CS!!)

    fun search(imageDto: ImageDto, params: Map<String, String?>) : List<ProductDto>{
        imageRobot.authenticate()
        val text = imageRobot.imageAnalyzer(imageDto)

        return emptyList()
//        productSearchRobot.authenticate()
//        return productSearchRobot.search(text, params)
    }

    fun searchByText(text: String, params: Map<String, String?>) : List<ProductDto>{
        productSearchRobot.authenticate()
        return productSearchRobot.search(text, params)
    }

    fun analyseImage(imageDto: ImageDto) : ClassifiedImages {
        imageRobot.authenticate()
        val results = imageRobot.imageAnalyzer(imageDto)

        return results!!
    }
}
