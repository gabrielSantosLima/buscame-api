package com.app.buscameapi.robots

import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.dto.ProductDto

class Orchestrator {
    private val imageRobot = ImageRobot()
    private val translateRobot = TranslateRobot()
    private val productSearchRobot = ProductSearchRobot()

    fun search(imageDto: ImageDto, params: Map<String, String?>) : List<ProductDto>{
        val terms = analyseImage(imageDto)
        val sentence = translateAllToSentence(terms)
        return search(sentence, params)
    }

    fun search(text: String, params: Map<String, String?>) = productSearchRobot.search(text, params)

    fun analyseImage(imageDto: ImageDto) = imageRobot.imageAnalyzer(imageDto)

    fun translateAllToSentence(terms : List<String>) : String{
        var sentence = ""
        terms.forEach {
            val translation = translate(it)
            sentence += translation
        }
        return sentence
    }

    fun translate(term : String) = translateRobot.translate(term)
}
