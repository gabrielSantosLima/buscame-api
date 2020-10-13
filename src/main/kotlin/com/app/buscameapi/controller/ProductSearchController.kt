package com.app.buscameapi.controller

import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.dto.Search
import com.app.buscameapi.robots.Orchestrator
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.io.*

@RestController
@RequestMapping("/api/search")
class ProductSearchController {

    private val orchestrator = Orchestrator()

    @PostMapping("/text")
    fun searchByText(@RequestParam text : String?,
                     @RequestParam url : String?,
                     @RequestParam price : String?,
                     @RequestParam brandName : String?
    ) : Search?{

        text ?: return null

        val params = mapOf("url" to url, "price" to price, "brandName" to brandName)

        return orchestrator.searchByText(text, params)
    }

    @PostMapping("/image")
    fun searchByImage(@RequestBody image : ImageDto?,
                      @RequestParam url : String?,
                      @RequestParam price : String?,
                      @RequestParam brandName : String?
    ) : Search?{

        image?.content ?: return null

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
