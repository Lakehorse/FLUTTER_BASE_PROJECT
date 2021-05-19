package com.theagilemonkeys.notes.api.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import com.horizen.api.http.ApiResponse
import com.horizen.serialization.Views

@JsonView(Views.Default::class)
data class ErrorResponse(@JsonProperty("description") val description: String): ApiResponse