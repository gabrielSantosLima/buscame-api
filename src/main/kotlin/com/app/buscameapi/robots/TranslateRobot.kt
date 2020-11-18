package com.app.buscameapi.robots

import com.app.buscameapi.util.PropertiesOfFile
import com.ibm.cloud.sdk.core.security.IamAuthenticator
import com.ibm.watson.language_translator.v3.LanguageTranslator
import com.ibm.watson.language_translator.v3.model.TranslateOptions
import com.ibm.watson.language_translator.v3.util.Language.ENGLISH
import com.ibm.watson.language_translator.v3.util.Language.PORTUGUESE

class TranslateRobot : ITranslateRobot{

    private val API_KEY = PropertiesOfFile.getProperty("LT_APIKEY")
    private val SERVICE_URL = PropertiesOfFile.getProperty("LT_URL")

    override fun authenticate() : Any{
        val authenticator = IamAuthenticator(API_KEY)
        val service = LanguageTranslator("2020-09-30", authenticator)
        service.serviceUrl = SERVICE_URL
        return service
    }

    override fun translate(text: String): String {
        val service = authenticate() as LanguageTranslator
        val translateOptions: TranslateOptions = TranslateOptions.Builder()
                .addText(text)
                .source(ENGLISH)
                .target(PORTUGUESE)
                .build()

        val firstWordTranslation = service?.translate(translateOptions)
                ?.execute()
                ?.getResult()
                ?.translations
                ?.get(0)
                ?.translation
                .toString()

        return firstWordTranslation
    }
}