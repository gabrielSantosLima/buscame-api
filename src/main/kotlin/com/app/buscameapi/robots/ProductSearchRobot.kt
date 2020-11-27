package com.app.buscameapi.robots

import com.app.buscameapi.dto.ProductDto
import com.app.buscameapi.dto.jsonRepresentations.Search
import com.app.buscameapi.util.PropertiesOfFile
import com.app.buscameapi.util.randomNumberHex
import org.springframework.web.client.RestTemplate
import java.io.File
import java.nio.file.Files

class ProductSearchRobot : IProductSearchRobot {

    private var API_KEY = PropertiesOfFile.getProperty("CS_APIKEY")
    private var SERVICE_URL = PropertiesOfFile.getProperty("CS_URL")
    private var CONTEXT_KEY = PropertiesOfFile.getProperty("CS_CONTEXTKEY")
    private var FIELDS_FILTER = "items(title,link,pagemap(offer(price),cse_image))"
    private var TYPEOF_URL_FILTER = "e"
    private var NUMBER_OF_RESULTS = 10
    private var URL = ""
    private val restTemplate = RestTemplate();

    override fun authenticate() : Any{
        val url = "$SERVICE_URL?key=$API_KEY&cx=$CONTEXT_KEY&num=$NUMBER_OF_RESULTS&fields=$FIELDS_FILTER"
        return url
    }

    override fun search(text: String, params: Map<String, String?>, page: Int) : List<ProductDto> {
        if (text.isEmpty()) return emptyList()
        val authenticate = authenticate() as String
        var pageOfSearch = page

        if(pageOfSearch > NUMBER_OF_RESULTS) pageOfSearch %= NUMBER_OF_RESULTS
        var URL = authenticate + "&q=${text}&start=$pageOfSearch"

        if(params["url"] != null) URL += "&siteSearchFilter=$TYPEOF_URL_FILTER&siteSearch=" + params["url"]

        try{
            val result = restTemplate.getForObject(URL, Search::class.java)
            result ?: return emptyList()
            return formatResult(result, text)
        }catch (e : Exception){
            e.printStackTrace()
            return emptyList()
        }
    }

    private fun formatResult(result : Search, text: String) : List<ProductDto>{
        return result.items.map {
            val title = it.title
            val price = it.pagemap?.offer?.get(0)?.price?.replace(",", ".")?.toDouble()
            val description = it.title
            val url = it.link
            val image = it.pagemap?.cse_image?.get(0)?.src

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
