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
                is