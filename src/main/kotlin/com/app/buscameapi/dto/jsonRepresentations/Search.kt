package com.app.buscameapi.dto.jsonRepresentations

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Search(val items : List<Item>)
