package com.theagilemonkeys.notes.transactions.serializers

import com.horizen.transaction.TransactionSerializer
import com.theagilemonkeys.notes.transactions.NoteCreatedTransaction
import scorex.util.serialization.Reader
import scorex.util.serialization.Writer

class NoteCreatedTransactionSerializer : TransactionSerializer<NoteCreatedTransaction> {
    override fun serialize(transaction: NoteCreatedTransaction, writer: Writer) {
        transaction.serialize(writer)
    }
    
    override fun parse(reader: Reader): NoteCreatedTransaction {
        return NoteCreatedTransaction.parse(reader)
    }
}