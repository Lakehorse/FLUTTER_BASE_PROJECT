
package com.theagilemonkeys.notes.boxes.serializers

import com.horizen.box.BoxSerializer
import com.theagilemonkeys.notes.boxes.NoteBox
import com.theagilemonkeys.notes.boxes.data.serializers.NoteBoxDataSerializer
import scorex.util.serialization.Reader
import scorex.util.serialization.Writer

class NoteBoxSerializer : BoxSerializer<NoteBox> {
    override fun serialize(box: NoteBox, writer: Writer) {
        box.data.serializer().serialize(box.data, writer)
        writer.putLong(box.nonce())
    }

    override fun parse(reader: Reader): NoteBox = NoteBox(
        NoteBoxDataSerializer().parse(reader),
        reader.long
    )
}