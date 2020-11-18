package com.app.buscameapi.robots

import com.ibm.cloud.sdk.core.security.Authenticator
import com.ibm.cloud.sdk.core.security.IamAuthenticator
import com.ibm.watson.language_translator.v3.LanguageTranslator
import com.ibm.watson.language_translator.v3.model.TranslateOptions
import com.ibm.watson.language_translator.v3.util.Language.ENGLISH
import com.ibm.watson.language_translator.v3.util.Language.PORTUGUESE

class TranslateRobot(override val API_KEY: String, override val SERVICE_URL: String) : ITranslateRobot{
    private lateinit var authenticator : Authenticator
    private var service: LanguageTranslator ? = null
    override fun authenticate() {
        authenticator = IamAuthenticator(API_KEY)
        service = LanguageTranslator("2020-09-30", authenticator)
        service?.setServiceUrl(SERVICE_URL)!!
    }

    override fun translate(text: String): String {
        authenticate()
        val translateOptions: TranslateOptions = TranslateOptions.Builder()
                .addText(text)
                .source(ENGLISH)
                .target(PORTUGUESE)
                .build()
        return service?.translate(translateOptions)
                ?.execute()
                ?.getResult()
                ?.translations
                ?.get(0)
                ?.translation
                .toString()
    }
}