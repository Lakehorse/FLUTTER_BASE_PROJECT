package com.theagilemonkeys.notes.transactions

import com.horizen.box.Box
import com.horizen.box.data.BoxData
import com.horizen.box.data.ZenBoxData
import com.horizen.proof.Signature25519
import com.horizen.proposition.Proposition
import com.horizen.transaction.AbstractRegularTransaction
import com.theagilemonkeys.notes.extensions.bytesMutableList
import com.theagilemonkeys.notes.extensions.serialize
import com.theagilemonkeys.notes.transactions.serializers.NoteDeletedTransactionSerializer
import scorex.core.`NodeViewModifier$`
import scorex.core.serialization.BytesSerializable
import scorex.core.serialization.ScorexSerializer
import scorex.util.serialization.Reader
import scorex.util.serialization.Writer

class NoteDeletedTransaction(
    val fundingInputsIDs: MutableList<ByteArray>,
    fundingInputsProofs: List<Signature25519>,
    changeOutputs: List<ZenBoxData>,
    fee: Long,
    private val version: Byte
) : AbstractRegularTransaction(fundingInputsIDs, fundingInputsProofs, changeOutputs, fee) {
    companion object {
        const val currentVersion = 1

        fun parse(reader: Reader): NoteDeletedTransaction {
            return NoteDeletedTransaction(
                reader.bytesMutableList(`NodeViewModifier$`.`MODULE$`.ModifierIdSize()),
                zenBoxProofsSerializer.parse(reader),
                zenBoxDataListSerializer.parse(reader),
                reader.long,
                reader.byte
            )
        }
    }

    override fun serializer(): ScorexSerializer<BytesSerializable> =
        N