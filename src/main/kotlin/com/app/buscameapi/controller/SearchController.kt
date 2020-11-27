package com.app.buscameapi.controller

import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.dto.ProductDto
import com.app.buscameapi.robots.Orchestrator
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.io.*

@RestController
@RequestMapping("/api/search")
class SearchController {

    private val orchestrator = Orchestrator()

    @PostMapping("/text")
    fun searchByText(@RequestParam text : String?,
                     @RequestParam url : String?,
                     @RequestParam price : String?,
                     @RequestParam brandName : String?,
                     @RequestParam page : Int?
    ) : List<ProductDto>{
        text ?: return emptyList()
        val params = mapOf("url" to url, "price" to price, "brandName" to brandName)

        page ?: return orchestrator.search(text, params)

        return orchestrator.search(text, params, page)
    }

    @PostMapping(value = ["/image"], consumes = [ MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE ])
    fun searchByImage(@RequestBody image : ByteArray,
                      @RequestParam url : String?,
                      @RequestParam price : String?,
                      @RequestParam brandName : String?,
                      @RequestParam page : Int?
    ) : List<ProductDto>{
        val params = mapOf("url" to url, "price" to price, "brandName" to brandName)
        val file : File = createTempFile()
        file.writeBytes(image)
        val imageDto = ImageDto(file)
        imageDto.content ?: return emptyList()

        page ?: return orchestrator.search(imageDto, params)

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
