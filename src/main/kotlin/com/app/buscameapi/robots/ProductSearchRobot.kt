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

    private val restTemplate = RestTemplate()

    @Throws
    override fun search(text: String, params: Map<String, String?>) : List<ProductDto> {
        if (text.isEmpty()) return emptyList()

        var products: List<ProductDto>
        val URL = getUrl(text)

        try{
            val result = restTemplate.getForObject(URL, Search::class.java)
            result ?: return emptyList()
            products = result.items.map {
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
        }catch(e: Exception){
            e.printStackTrace()
            return emptyList()
        }
        return products
    }

    private fun getUrl(text: String) = "${SERVICE_URL}?key=${API_KEY}&cx=${CS_CONTEXTKEY}&q=${text}"
}
