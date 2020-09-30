package com.app.buscameapi.robots

import com.app.buscameapi.dto.ImageDto
import com.google.protobuf.compiler.PluginProtos
import com.ibm.cloud.sdk.core.security.Authenticator
import com.ibm.cloud.sdk.core.security.IamAuthenticator
import com.ibm.watson.visual_recognition.v3.VisualRecognition
import com.ibm.watson.visual_recognition.v3.model.ClassifyOptions
import com.ibm.watson.visual_recognition.v4.model.AnalyzeOptions
import java.io.File
import java.io.FileInputStream

class ImageRobot(override val API_KEY: String, override val SERVICE_URL: String) : IImageRobot {

    private var authenticator :  Authenticator? = null
    private lateinit var service: VisualRecognition

    override fun authenticate() {
        authenticator = IamAuthenticator(API_KEY)
        service = VisualRecognition("2020-09-30", authenticator)
        service?.setServiceUrl(SERVICE_URL)
    }

    @Throws(Exception::class)
    override fun imageAnalyzer(file: ImageDto) : String {

        authenticator ?: return ""

        val imageStream = FileInputStream(file.content)

        val options = ClassifyOptions.Builder()
                .imagesFile(imageStream)
                .build()

        val result = service.classify(options).execute().result

        return result.toString()
    }
}
