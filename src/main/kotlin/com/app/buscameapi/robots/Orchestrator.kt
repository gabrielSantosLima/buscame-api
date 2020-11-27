package com.app.buscameapi.robots

import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.dto.ProductDto

class Orchestrator {
    private val imageRobot = ImageRobot()
    private val translateRobot = TranslateRobot()
    private val productSearchRobot = ProductSearchRobot()

    fun search(imageDto: ImageDto, params: Map<String, String?>, page: Int = 0) : List<ProductDto>{
        val terms = analyseImage(imageDto)
        val sentence = translateAllToSentence(terms)
        return search(sentence, params, page)
    }

    fun search(text: String, params: Map<String, String?>, page: Int = 0) = productSearchRobot.search(text, params, page)

    fun analyseImage(imageDto: ImageDto) = imageRobot.imageAnalyzer(imageDto)

    private fun translateAllToSentence(terms : List<String>) : String{
        var sentence = ""
        terms.forEach {
            val translation = translate(it)
            sentence += translation
        }
        return sentence
    }

    private fun translate(term : String) = translateRobot.translate(term)
}
