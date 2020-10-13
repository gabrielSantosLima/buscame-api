package com.app.buscameapi.robots

import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.dto.Search
import com.app.buscameapi.util.Properties
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.Exception

class Orchestrator {

    private val credentials = Properties("src/main/resources/credentials.properties")
    private val values = Properties("src/main/resources/values.properties")

    private val VG_APIKEY = credentials?.getProperty("VG_APIKEY")
    private val CS_APIKEY = credentials?.getProperty("CS_APIKEY")
    private val CS_CONTEXTKEY = credentials?.getProperty("CS_CONTEXTKEY")
    private val VG_URL = values?.getProperty("VG_URL")
    private val CS_URL = values?.getProperty("CS_URL")

    private val imageRobot = ImageRobot(
            VG_APIKEY!!,
            VG_URL!!
    )

    private val productSearchRobot = ProductSearchRobot(
            CS_APIKEY!!,
            CS_CONTEXTKEY!!,
            CS_URL!!
    )

    fun search(imageDto: ImageDto, params: Map<String, String?>) : Search?{
        imageRobot.authenticate()
        val text = imageRobot.imageAnalyzer(imageDto)

        return null
//        return productSearchRobot.search(text, params)
    }

    fun searchByText(text: String, params: Map<String, String?>) : Search?{
        var result: Search?

        try{
            result = productSearchRobot.search(text, params)
        }catch(error: Exception){
            print("[orchestrator] Erro: $error")
            return null
        }

        return result
    }

    fun analyseImage(imageDto: ImageDto) : ClassifiedImages {
        imageRobot.authenticate()
        val results = imageRobot.imageAnalyzer(imageDto)

        return results!!
    }
}
