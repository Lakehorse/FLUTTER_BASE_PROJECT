package com.theagilemonkeys.notes.transactions

import com.horizen.box.Box
import com.horizen.box.data.BoxData
import com.horizen.box.data.ZenBoxData
import com.horizen.proof.Signature25519
import com.horizen.proposition.Proposition
import com.horizen.transaction.AbstractRegularTransaction
import com.theagilemonkeys.notes.boxes.data.NoteBoxData
import com.theagilemonkeys.notes.boxes.data.serializers.NoteBoxDataSerializer
import com.theagilemonkeys.notes.extensions.bytesMutableList
import com.theagilemonkeys.notes.extensions.serialize
import com.theagilemonkeys.notes.transactions.serializers.NoteCreatedTransactionSerializer
import scorex.core.`NodeViewModifier$`
import scorex.core.serialization.BytesSerializable
import scorex.core.serialization.ScorexSerializer
import scorex.util.serialization.Reader
import scorex.util.serialization.Writer

class NoteCreatedTransaction(
    fundingInputsIDs: MutableList<ByteArray>,
    fundingInputsProofs: List<Signature25519>,
    changeOutputs: List<ZenBoxData>,
    fee: Long,
    val data: NoteBoxData,
    val version: Byte
) : AbstractRegularTransaction(fundingInputsIDs, fundingInputsProofs, changeOutputs, fee) {
    companion object {
        const val currentVersion = 1

        fun parse(reader: Reader): NoteCreatedTransaction {
            return NoteCreatedTransaction(
                reader.bytesMutableList(`NodeViewModifier$`.`MODULE$`.ModifierIdSize()),
                zenBoxProofsSerializer.parse(reader),
                zenBoxDataListSerializer.parse(reader),
                reader.long,
                NoteBoxDataSerializer().parse(reader),
                reader.byte
            )
        }
    }

    override fun serializer(): ScorexSerializer<BytesSerializable> =
        NoteCreatedTransactionSerializer() as ScorexSerializer<BytesSerializable>

    override fun transactionTypeId(): Byte = NotesAppTransactions.NoteCreated.id

    override fun version(): Byte = version

    override fun customFieldsData(): ByteArray = data.bytes()

    override fun customDataMessageToSign(): ByteArray = ByteArray(0)

    override fun getCustomOutputData(): MutableList<BoxData<Proposition, Box<Proposition>>> =
        mutableListOf(data as BoxData<Proposition, Box<Proposition>>)

    fun serialize(writer: Writer) {
        inputZenBoxIds.serialize(writer)
        zenBoxProofsSerializer.serialize(inputZenBoxProofs, writer)
        zenBoxDataListSerializer.serialize(outputZenBoxesData, writer)
        writer.putLong(fee)
        NoteBoxDataSerializer().serialize(data, writer)
        writer.put(version)
    }
}