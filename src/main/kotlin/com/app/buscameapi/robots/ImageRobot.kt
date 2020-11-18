package com.app.buscameapi.robots

import com.app.buscameapi.dto.ClassifierDto
import com.app.buscameapi.dto.ImageDto
import com.app.buscameapi.util.PropertiesOfFile
import com.ibm.cloud.sdk.core.security.IamAuthenticator
import com.ibm.watson.visual_recognition.v3.VisualRecognition
import com.ibm.watson.visual_recognition.v3.model.ClassifyOptions

class ImageRobot : IImageRobot {

    private val API_KEY = PropertiesOfFile.getProperty("VG_APIKEY")
    private val SERVICE_URL = PropertiesOfFile.getProperty("VG_URL")
    private val SCORE_SEARCH = 0.7

    override fun authenticate() : Any{
        val authenticator = IamAuthenticator(API_KEY)
        val service = VisualRecognition("2020-09-30", authenticator)
        service.serviceUrl = SERVICE_URL
        return service
    }

    override fun imageAnalyzer(image: ImageDto) : List<String> {
        val service = authenticate() as VisualRecognition
        val options = ClassifyOptions.Builder()
                .imagesFile(image.content)
                .build()
        val classifiedImages = service.classify(options).execute().result
        val classifiers = classifiedImages
                .images[0]
                .classifiers[0]
                .classes.map { ClassifierDto(it.score.toDouble(), it.typeHierarchy, it.xClass) }
        return getClassifications(classifiers)
    }

    private fun getClassifications(classifiers : List<ClassifierDto>) : List<String>{
        val filteredClassifiers = classifiers.filter{ it.score > SCORE_SEARCH }
        return filteredClassifiers.map { it.classification }
    }
}
