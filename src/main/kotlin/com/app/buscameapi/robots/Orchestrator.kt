package com.app.buscameapi.robots

import com.app.buscameapi.dto.ClassifierDto
import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.dto.ProductDto
import com.app.buscameapi.util.Properties

class Orchestrator {
    private val SCORE_SEARCH = 0.7
    private val credentials = Properties("src/main/resources/credentials.properties")
    private val values = Properties("src/main/resources/values.properties")
    private val VG_APIKEY = credentials?.getProperty("VG_APIKEY")
    private val VG_URL = values?.getProperty("VG_URL")
    private val LT_APIKEY = credentials?.getProperty("LT_APIKEY")
    private val LT_URL = values?.getProperty("LT_URL")
    private val CS_APIKEY = credentials?.getProperty("CS_APIKEY")
    private val CS_CONTEXTKEY = credentials?.getProperty("CS_CONTEXTKEY")
    private val CS_URL = values?.getProperty("CS_URL")

    private val imageRobot = ImageRobot(
            VG_APIKEY!!,
            VG_URL!!
    )

    private val translateRobot = TranslateRobot(
            LT_APIKEY!!,
            LT_URL!!
    )

    private val productSearchRobot = ProductSearchRobot(
            CS_APIKEY!!,
            CS_CONTEXTKEY!!,
            CS_URL!!
    )

    fun search(imageDto: ImageDto, params: Map<String, String?>) : List<ProductDto>{
        val classifiers = analyseImage(imageDto)
        val allowedClassifiers = classifiers.filter{ it.score > SCORE_SEARCH }
        val terms = allowedClassifiers.map { it.classification }
        var finalTerm = ""
        terms.forEach {
        val translation = translateRobot.translate(it)
            finalTerm += "${translation.replace(".", "")
                    .replace("(","")
                    .replace(")", "")
            } "
        }
        return search(finalTerm, params)
    }

    fun search(text: String, params: Map<String, String?>) : List<ProductDto>{
        return productSearchRobot.search(text, params)
    }

    fun analyseImage(imageDto: ImageDto) : List<ClassifierDto> {
        return imageRobot.imageAnalyzer(imageDto)
    }
}
