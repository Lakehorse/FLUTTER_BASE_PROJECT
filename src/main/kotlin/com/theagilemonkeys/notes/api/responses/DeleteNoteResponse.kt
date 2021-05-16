package com.theagilemonkeys.notes.api.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import com.horizen.serialization.Views

@JsonView(Views.Default::class)
data class DeleteNoteResponse(@JsonProperty("transactionBytes") override val transactionBytes: String): NoteAPIResponse