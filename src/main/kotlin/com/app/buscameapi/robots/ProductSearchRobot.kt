package com.app.buscameapi.robots

import com.app.buscameapi.dto.ProductDto
import com.app.buscameapi.dto.jsonRepresentations.Search
import com.app.buscameapi.util.PropertiesOfFile
import com.app.buscameapi.util.randomNumberHex

class ProductSearchRobot : IProductSearchRobot {

    private val API_KEY = PropertiesOfFile.getProperty("CS_APIKEY")
    private val SERVICE_URL = PropertiesOfFile.getProperty("_URL")
    private val URL = PropertiesOfFile.getProperty("CS_CONTEXTKEY")

    override fun authenticate() : Any{
        val url = "${SERVICE_URL}?key=${API_KEY}&cx=${CS_CONTEXTKEY}"
        return url
    }

    override fun search(text: String, params: Map<String, String?>) : List<ProductDto> {
        if (text.isEmpty()) return emptyList()
        val URL = authenticate + "&q=${text}"
        try{
            val result = restTemplate.getForObject(URL, Search::class.java)
            result ?: return emptyList()
            return formatResult(result)
        }catch (e : Exception){
            return emptyList()
        }
    }

    private fun formatResult(result : Search){
        return result.items.map {
            val title = it.title
            val price = it.pagemap?.offer?.get(0)?.price?.replace("R\$", "")?.toDouble()
            val description = it.title
            val url = it.link
            val image = null

            ProductDto(
                    randomNumberHex(),
                    text,
                    title,
                    price,
                    description,
                    url,
                    image
            )
        }
    }
}
