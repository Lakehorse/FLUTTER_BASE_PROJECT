
package com.theagilemonkeys.notes.transactions.serializers

import com.horizen.transaction.TransactionSerializer
import com.theagilemonkeys.notes.transactions.NoteDeletedTransaction
import scorex.util.serialization.Reader
import scorex.util.serialization.Writer

class NoteDeletedTransactionSerializer : TransactionSerializer<NoteDeletedTransaction> {
    override fun serialize(transaction: NoteDeletedTransaction, writer: Writer) {
        transaction.serialize(writer)
    }

    override fun parse(reader: Reader): NoteDeletedTransaction {
        return NoteDeletedTransaction.parse(reader)
    }
}