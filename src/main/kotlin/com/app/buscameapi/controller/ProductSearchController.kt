package com.app.buscameapi.controller

import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.dto.ProductDto
import com.app.buscameapi.robots.Orchestrator
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.io.*
import java.nio.ByteBuffer
import java.nio.file.Files

@RestController
@RequestMapping("/api/search")
class ProductSearchController {

    private val orchestrator = Orchestrator()

    @PostMapping("/text")
    fun searchByText(@RequestBody text : String?,
                     @RequestParam url : String?,
                     @RequestParam price : String?,
                     @RequestParam brandName : String?
    ) : List<ProductDto>{

        text ?: return emptyList()

        val params = mapOf("url" to url, "price" to price, "brandName" to brandName)

        return orchestrator.searchByText(text, params)
    }

    @PostMapping("/image")
    fun searchByImage(@RequestBody image : ImageDto?,
                      @RequestParam url : String?,
                      @RequestParam price : String?,
                      @RequestParam brandName : String?
    ) : List<ProductDto>{

        image?.content ?: return emptyList()

        val params = mapOf("url" to url, "price" to price, "brandName" to brandName)

        return orchestrator.search(image, params)
    }

    @PostMapping(value = ["/image-analyse"], consumes = [ MediaType.IMAGE_JPEG_VALUE])
    fun searchByImage(@RequestBody image : ByteArray) : ClassifiedImages?{

        val file : File = createTempFile()
        file.writeBytes(image)

        val imageDto = ImageDto(file)

        imageDto?.content ?: return null

        return orchestrator.analyseImage(imageDto)
    }
}
