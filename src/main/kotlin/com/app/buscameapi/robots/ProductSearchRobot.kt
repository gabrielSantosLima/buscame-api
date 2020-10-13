package com.app.buscameapi.robots

import com.app.buscameapi.dto.Search
import org.springframework.web.client.RestTemplate

class ProductSearchRobot (
        override val API_KEY: String,
        override val CS_CONTEXTKEY: String,
        override val SERVICE_URL: String
) : IProductSearchRobot {

    private var URL = "https://www.googleapis.com/customsearch/v1?key=${API_KEY}&cx=${CS_CONTEXTKEY}"

    private val restTemplate = RestTemplate()

    override fun search(text: String, params: Map<String, String?>) : Search {
        URL += "&q=${text}"

        return restTemplate.getForObject(URL, Search::class.java)!!
    }
}