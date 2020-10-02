package com.app.buscameapi.util

import java.io.FileInputStream
import java.util.Properties

class Properties(private val path : String){

    private val file = FileInputStream(path)
    private val prop = Properties()

    init {
        load()
    }

    private fun load(){
        try{
            prop.load(file)
        }catch(e: Exception) {
            error("[properties] Erro ao ler arquivo de caminho $path : ${e.message}")
        }
    }

    fun getProperty(name : String) : String?{
        return prop.getProperty(name)
    }
}