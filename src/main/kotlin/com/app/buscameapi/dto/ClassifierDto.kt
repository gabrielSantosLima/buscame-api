package com.app.buscameapi.dto

data class ClassifierDto(
        val score : Double,
        val typeHierarchy: String?,
        val classification: String
)