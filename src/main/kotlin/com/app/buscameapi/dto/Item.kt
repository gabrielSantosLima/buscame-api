package com.app.buscameapi.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Item(
        val title: String,
        val link: String,
        val pagemap: Map?
)

@JsonIgnoreProperties(ignoreUnknown = true)
class Map(
        val offer: Array<Price?>?,
        val cse_image: Array<Image>?
)

@JsonIgnoreProperties(ignoreUnknown = true)
class Price(
        val pricecurrency :String,
        val price :String
)

@JsonIgnoreProperties(ignoreUnknown = true)
class Image(
        val src: String
)