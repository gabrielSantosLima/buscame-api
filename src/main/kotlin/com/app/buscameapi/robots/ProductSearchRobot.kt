package com.app.buscameapi.robots

import com.app.buscameapi.dto.ProductDto
import com.app.buscameapi.dto.jsonRepresentations.Search
import com.app.buscameapi.util.randomNumberHex
import org.springframework.web.client.RestTemplate
import java.io.File
import kotlin.jvm.Throws

class ProductSearchRobot (
        override val API_KEY: String,
        override val CS_CONTEXTKEY: String,
        override val SERVICE_URL: String
) : IProductSearchRobot {

    private var URL = "https://www.googleapis.com/customsearch/v1?key=${API_KEY}&cx=${CS_CONTEXTKEY}"

    private val restTemplate = RestTemplate()

    @Throws
    override fun search(text: String, params: Map<String, String?>) : List<ProductDto> {
        URL += "&q=$text"

        val result = restTemplate.getForObject(URL, Search::class.java)

        result ?: return emptyList()

        val products = result.items.map {

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

        return products
    }
}
