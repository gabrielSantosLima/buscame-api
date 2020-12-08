package com.app.buscameapi.controller

import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.dto.ProductDto
import com.app.buscameapi.robots.ImageRobot
import com.app.buscameapi.robots.Orchestrator
import com.app.buscameapi.robots.ProductSearchRobot
import com.app.buscameapi.robots.TranslateRobot
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.io.*

@RestController
@RequestMapping("/api/search")
class SearchController{

    private var orchestrator: Orchestrator = Orchestrator(
            ImageRobot(),
            TranslateRobot(),
            ProductSearchRobot()
    )

    @PostMapping("/text")
    fun searchByText(@RequestParam text : String?,
                     @RequestParam(required = false, defaultValue = "") url : String,
                     @RequestParam(required = false, defaultValue = "0.0") price : String,
                     @RequestParam(required = false, defaultValue = "") brandName : String,
                     @RequestParam(required = false, defaultValue = "1") page : Int
    ) : List<ProductDto>{
        text ?: return emptyList()
        val params = mapOf("url" to url, "price" to price, "brandName" to brandName)
        return orchestrator.search(text, params, page)
    }

    @PostMapping(value = ["/image"], consumes = [ MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE ])
    fun searchByImage(@RequestBody image : ByteArray,
                      @RequestParam(required = false, defaultValue = "") url : String,
                      @RequestParam(required = false, defaultValue = "0.0") price : String,
                      @RequestParam(required = false, defaultValue = "") brandName : String,
                      @RequestParam(required = false, defaultValue = "1") page : Int
    ) : List<ProductDto>{
        val params = mapOf("url" to url, "price" to price, "brandName" to brandName)
        val file = createTempFile()
        file.writeBytes(image)
        val imageDto = ImageDto(file)
        imageDto.content ?: return emptyList()
        return orchestrator.search(imageDto, params, page)
    }

    @PostMapping(value = ["/image-analyse"], consumes = [ MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE])
    fun searchByImage(@RequestBody image : ByteArray) : List<String>{
        val file : File = createTempFile()
        file.writeBytes(image)
        val imageDto = ImageDto(file)
        imageDto.content ?: return emptyList()
        return orchestrator.analyseImage(imageDto)
    }
}
