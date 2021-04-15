package com.theagilemonkeys.notes.api.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class DeleteNoteRequest(
    @JsonProperty("id") val id: String,
    @JsonProperty("proposition") override val proposition: String,
    @JsonProperty("fee") override val fee: Long
) : BaseRequest