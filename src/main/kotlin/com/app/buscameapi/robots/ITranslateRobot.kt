package com.app.buscameapi.robots

interface ITranslateRobot: IRobot, IAuthenticateRobot {
    fun translate(text: String) : String
}