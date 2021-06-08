package com.theagilemonkeys.notes.api.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import com.horizen.serialization.Views

@JsonView(Views.Default::class)
data class UpdateNoteResponse(
    @JsonProperty("id") val id: String,
    @JsonProperty("transactionBytes") override val transactionBytes: String
) : NoteAPIResponse