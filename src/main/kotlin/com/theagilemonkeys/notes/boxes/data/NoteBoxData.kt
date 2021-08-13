package com.theagilemonkeys.notes.boxes.data

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import com.horizen.box.data.AbstractBoxData
import com.horizen.proposition.PublicKey25519Proposition
import com.horizen.serialization.Views
import com.theagilemonkeys.notes.boxes.NoteBox
import com.theagilemonkeys.notes.boxes.data.serializers.NoteBoxDataSerializer

@JsonView(Views.Default::class)
d