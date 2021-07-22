package com.theagilemonkeys.notes.boxes

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import com.horizen.box.AbstractBox
import com.horizen.proposition.PublicKey25519Proposition
import com.horizen.serialization.Views
import com.theagilemonkeys.notes.boxes.data.NoteBoxData
import com.theagilemonkeys.notes.boxes.serializers.NoteBoxSerializer

@JsonView(Views.Default::class)
data class NoteBox(@JsonProperty("data") val data: NoteBoxData, @JsonProperty("nonce") val nonce: Long) :
    AbstractBox<PublicKey25519Proposition, NoteBoxData, NoteBox>(data, nonce) {
    override fun serializer() = NoteBoxSerializer()
    override fun boxTypeId(): Byte = NotesAppBoxes.NoteBox.id
}