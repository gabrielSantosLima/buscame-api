package com.app.buscameapi.util

import java.io.FileInputStream
import java.util.Properties

class PropertiesOfFile{
    companion object{
        private const val CREDENTIALS_PATH = "src/main/resources/credentials.properties"
        private fun load() : Properties{
            try{
                val fileInputStream = FileInputStream(CREDENTIALS_PATH)
                val propertiesOfFile = Properties()
                propertiesOfFile.load(fileInputStream)
                return propertiesOfFile
            }catch(e: Exception) {
                error("[properties] Erro ao ler arquivo de caminho $CREDENTIALS_PATH : ${e.message}")
            }
        }

        fun getProperty(name : String) : String?{
            val propertiesOfFile = load()
            return propertiesOfFile.getProperty(name)
        }
    }

}