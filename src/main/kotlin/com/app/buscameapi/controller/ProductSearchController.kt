package com.app.buscameapi.controller

import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.dto.ProductDto
import com.app.buscameapi.robots.Orchestrator
import org.springframework.web.bind.annotation.*

@SuppressWarnings
@RestController
@RequestMapping("/api/search")
class ProductSearchController {

    private val orchestrator = Orchestrator()

    @PostMapping("/text")
    fun searchByText(text : String?, @RequestParam url : String?, @RequestParam price : String?,
        @RequestParam brandName : String? ) : List<ProductDto>{

        text ?: return emptyList()

        val params = mapOf("url" to url, "price" to price, "brandName" to brandName)

        return orchestrator.searchByText(text, params)
    }

    @PostMapping("/image")
    fun searchByImage(image : ImageDto?, @RequestParam url : String?, @RequestParam price : String?,
        @RequestParam brandName : String?) : List<ProductDto>{

        image ?: return emptyList()

        val params = mapOf("url" to url, "price" to price, "brandName" to brandName)

        return orchestrator.search(image, params)
    }
}
