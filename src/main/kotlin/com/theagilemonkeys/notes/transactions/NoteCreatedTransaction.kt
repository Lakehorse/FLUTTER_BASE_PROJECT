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
    changeOutputs: