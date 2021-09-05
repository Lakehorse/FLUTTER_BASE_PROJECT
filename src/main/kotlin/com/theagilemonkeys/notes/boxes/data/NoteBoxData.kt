package com.theagilemonkeys.notes.boxes.data

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import com.horizen.box.data.AbstractBoxData
import com.horizen.proposition.PublicKey25519Proposition
import com.horizen.serialization.Views
import com.theagilemonkeys.notes.boxes.NoteBox
import com.theagilemonkeys.notes.boxes.data.serializers.NoteBoxDataSerializer

@JsonView(Views.Default::class)
data class NoteBoxData(
    @JsonProperty("proposition") val proposition: PublicKey25519Proposition,
    @JsonProperty("id") val id: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("content") val content: String,
    @JsonProperty("createdAt") val createdAt: Long
) : // Does it make sense to have to set nonce = 1 by default?
    AbstractBoxData<PublicKey25519Proposition, NoteBox, NoteBoxData>(proposition, 1) {

    override fun serializer() = NoteBoxDataSerializer()
    override fun getBox(nonce: Long): NoteBox = NoteBox(this, nonce)
}