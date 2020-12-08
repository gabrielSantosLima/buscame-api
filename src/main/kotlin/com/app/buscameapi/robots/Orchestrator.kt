package com.app.buscameapi.robots

import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.dto.ProductDto

class Orchestrator(
        private val imageRobot: IImageRobot,
        private val translateRobot: ITranslateRobot,
        private val productSearchRobot: IProductSearchRobot
){
    fun search(imageDto: ImageDto, params: Map<String, Any>, page: Int = 1) : List<ProductDto>{
        val terms = analyseImage(imageDto)
        val sentence = translateAllToSentence(terms)
        return search(sentence, params, page)
    }

    fun search(text: String, params: Map<String, Any>, page: Int = 1) : List<ProductDto> {
        return productSearchRobot.search(text, params, page)
    }

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
