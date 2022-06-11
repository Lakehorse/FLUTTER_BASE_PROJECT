
package com.theagilemonkeys.notes.transactions.serializers

import com.horizen.transaction.TransactionSerializer
import com.theagilemonkeys.notes.transactions.NoteUpdatedTransaction
import scorex.util.serialization.Reader
import scorex.util.serialization.Writer

class NoteUpdatedTransactionSerializer: TransactionSerializer<NoteUpdatedTransaction> {
    override fun serialize(transaction: NoteUpdatedTransaction, writer: Writer) {
        transaction.serialize(writer)
    }

    override fun parse(reader: Reader): NoteUpdatedTransaction {
        return NoteUpdatedTransaction.parse(reader)
    }
}