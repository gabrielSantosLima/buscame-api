package com.app.buscameapi.robots

import com.app.buscameapi.dto.ClassifierDto
import com.app.buscameapi.dto.ImageDto
import com.ibm.cloud.sdk.core.security.Authenticator
import com.ibm.cloud.sdk.core.security.IamAuthenticator
import com.ibm.watson.visual_recognition.v3.VisualRecognition
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages
import com.ibm.watson.visual_recognition.v3.model.ClassifyOptions
import java.io.FileInputStream

class ImageRobot(override val API_KEY: String, override val SERVICE_URL: String) : IImageRobot {

    private lateinit var authenticator :  Authenticator
    private var service: VisualRecognition? = null

    override fun authenticate() {
        authenticator = IamAuthenticator(API_KEY)
        service = VisualRecognition("2020-09-30", authenticator)
        service?.setServiceUrl(SERVICE_URL)!!
    }

    @Throws(Exception::class)
    override fun imageAnalyzer(image: ImageDto) : List<ClassifierDto> {
        authenticate()

        val options = ClassifyOptions.Builder()
                .imagesFile(image.content)
                .build()

        val classifiedImages : ClassifiedImages? = service?.classify(options)?.execute()?.result

        classifiedImages ?: return emptyList()

        val classifiers = classifiedImages.images[0].classifiers[0].classes?.map {
            ClassifierDto(it.score.toDouble(), it.typeHierarchy, it.xClass)
        }

        return classifiers!!
    }
}
