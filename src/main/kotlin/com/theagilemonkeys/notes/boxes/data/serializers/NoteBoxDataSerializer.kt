
package com.theagilemonkeys.notes.boxes.data.serializers

import com.horizen.box.data.BoxDataSerializer
import com.horizen.proposition.PublicKey25519PropositionSerializer
import com.theagilemonkeys.notes.boxes.data.NoteBoxData
import com.theagilemonkeys.notes.extensions.serialize
import com.theagilemonkeys.notes.extensions.string
import scorex.util.serialization.Reader
import scorex.util.serialization.Writer

class NoteBoxDataSerializer: BoxDataSerializer<NoteBoxData> {
    override fun serialize(data: NoteBoxData, writer: Writer) {
        PublicKey25519PropositionSerializer.getSerializer().serialize(data.proposition(), writer)
        data.id.serialize(writer)
        data.title.serialize(writer)
        data.content.serialize(writer)
        writer.putLong(data.createdAt)
    }

    override fun parse(reader: Reader): NoteBoxData = NoteBoxData(
        PublicKey25519PropositionSerializer.getSerializer().parse(reader),
        reader.string(),
        reader.string(),
        reader.string(),
        reader.long
    )
}