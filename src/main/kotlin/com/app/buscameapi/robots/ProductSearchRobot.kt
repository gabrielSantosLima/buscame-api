package com.app.buscameapi.robots

import com.app.buscameapi.dto.ProductDto
import com.app.buscameapi.dto.jsonRepresentations.Search
import com.app.buscameapi.util.PropertiesOfFile
import com.app.buscameapi.util.randomNumberHex
import org.springframework.web.client.RestTemplate

class ProductSearchRobot : IProductSearchRobot {

    private var API_KEY = PropertiesOfFile.getProperty("CS_APIKEY")
    private var SERVICE_URL = PropertiesOfFile.getProperty("CS_URL")
    private var CONTEXT_KEY = PropertiesOfFile.getProperty("CS_CONTEXTKEY")
    private var FIELDS_FILTER = "items(title,link,pagemap(offer(price),cse_image))"
    private var TYPEOF_URL_FILTER = "e"
    private var NUMBER_OF_RESULTS = 10
    private val restTemplate = RestTemplate()

    override fun authenticate() : Any{
        val url = "$SERVICE_URL?key=$API_KEY&cx=$CONTEXT_KEY"
        return concatUrlParams(url,mapOf(
                "num" to NUMBER_OF_RESULTS,
                "fields" to FIELDS_FILTER)
        )
    }

    override fun search(text: String, params: Map<String, Any>, page: Int) : List<ProductDto> {
        if (text.isEmpty()) return emptyList()
        val authenticatedUrl = authenticate() as String
        var pageOfSearch = page

        if(pageOfSearch > NUMBER_OF_RESULTS){
            pageOfSearch %= NUMBER_OF_RESULTS
        }

        var finalUrl = concatUrlParams(authenticatedUrl, mapOf(
                "q" to text,
                "start" to pageOfSearch)
        )

        if(params["url"] != null) {
            finalUrl = concatUrlParams(finalUrl, mapOf(
                    "siteSearchFilter" to TYPEOF_URL_FILTER,
                    "siteSearch" to (params["url"] ?: "")
            ))
        }

        try{
            val result = restTemplate.getForObject(finalUrl, Search::class.java)
            result ?: return emptyList()
            return formatResult(result, text, params)
        }catch (e : Exception){
            e.printStackTrace()
            return emptyList()
        }
    }

    private fun formatResult(
            result : Search,
            text: String,
            params: Map<String, Any>
    ) : List<ProductDto>{
        val priceFilter = if(params["price"] == null)  0.0 else params["price"].toString().toDouble()
        val brandNameFilter = if(params["brandName"] == null)  "" else params["brandName"] as String

        var applyFilters = result.items.filter {
            item -> item.title.contains(brandNameFilter)
        }

        applyFilters = applyFilters.filter {
            item -> item.pagemap?.offer?.get(0)?.price?.replace(",", ".")?.toDouble() !== null
                && item.pagemap?.offer?.get(0)?.price?.replace(",", ".")?.toDouble()!! >= priceFilter
        }

        return applyFilters.map {
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

    private fun concatUrlParams(url : String, params : Map<String,Any>) : String{
        var newUrl = url
        params.forEach {
            newUrl += "&${it.key}=${it.value}"
        }
        return newUrl
    }
}
