package com.theagilemonkeys.notes.api.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class DeleteNoteRequest(
    @JsonProperty("id") val id: