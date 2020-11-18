package com.app.buscameapi.robots

interface ITranslateRobot: IRobot {
    fun translate(text: String) : String
}