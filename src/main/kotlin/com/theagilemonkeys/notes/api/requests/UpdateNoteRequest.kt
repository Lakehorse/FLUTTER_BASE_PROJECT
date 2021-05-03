package com.theagilemonkeys.notes.api.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateNoteRequest(
    @JsonProperty("id") val id: String,
    @JsonProperty("content") val content: Strin