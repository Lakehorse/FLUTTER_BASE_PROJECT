
package com.theagilemonkeys.notes.api.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateNoteRequest(
    @JsonProperty("title") val title: String,
    @JsonProperty("content") val content: String,
    @JsonProperty("proposition") override val proposition: String,
    @JsonProperty("fee") override val fee: Long
) : BaseRequest