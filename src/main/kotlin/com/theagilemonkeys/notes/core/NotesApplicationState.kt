package com.theagilemonkeys.notes.core

import NoteCreatedValidator
import NoteDeletedValidator
import com.horizen.block.SidechainBlock
import com.horizen.box.Box
import com.horizen.proposition.Proposition
import com.horizen.state.ApplicationState
import com.horizen.state.SidechainStateReader
import com.horizen.transaction.BoxTransaction
import com.theagilemonkeys.notes.extensions.getOrThrow
import com.theagilemonkeys.notes.transactions.NoteCreatedTransaction
import com.theagilemonkeys.notes.transactions.NoteDeletedTransaction
import com.theagilemonkeys.notes.transactions.NoteUpdatedTransaction
import com.theagilemonkeys.notes.transactions.validators.NoteUpdatedValidator
import scala.collection.JavaConverters
import scala.util.Success
import scala.util.Try

class NotesApplicationState : ApplicationState {
    private val noteCreatedValidator = NoteCreatedValidator()
    private val noteUpdatedValidator = NoteUpdatedValidator()
    private val noteDeletedValidator = NoteDeletedValidator()

    override fun validate(stateReader: SidechainStateReader, block: SidechainBlock) {
        val createdTransactions = mutableListOf<String>()
        JavaConverters.seqAsJavaList(block.transactions()).forEach { transaction ->
            when (transaction) {
                is NoteCreatedTransaction -> {
                    if (createdTransactions.contains(transaction.data.id)) {
                        throw IllegalArgumentException("Transaction already registered")
                    } else {
                        createdTransactions += transaction.data.id
                    }
                }
                else -> return
            }
        }
    }

    override fun validate(
        stateReader: SidechainStateReader,
        transaction: BoxTransaction<Proposition, Box<Proposition>>
    ) {
        when (transaction) {
            is NoteCreatedTransaction -> {
                noteCreatedValidator.validate(stateReader, transaction).getOrThrow()
            }
            is NoteUpdatedTransaction -> {
                noteUpdatedValidator.validate(stateReader, transaction).getOrThrow()
            }
            is NoteDeletedTransaction -> {
                noteDeletedValidator.validate(stateReader, transaction).getOrThrow()
            }
            else -> {}
        }
    }

    // TODO: here we expect to update notes database. The data from it will be used during validation.
    override fun onApplyChanges(
        stateReader: SidechainStateReader?,
        blockId: ByteArray?,
        newBoxes: MutableList<Box<Proposition>>?,
        boxIdsToRemove: MutableList<ByteArray>?
    ): Try<ApplicationState> = Success(this)

    // TODO: rollback notes database to certain point.
    override fun onRollback(blockId: ByteArray?): Try<ApplicationState> =
        Success(this)
}