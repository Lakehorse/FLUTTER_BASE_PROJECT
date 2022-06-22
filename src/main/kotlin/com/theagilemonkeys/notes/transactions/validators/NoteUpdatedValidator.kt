
package com.theagilemonkeys.notes.transactions.validators

import com.horizen.state.SidechainStateReader
import com.theagilemonkeys.notes.boxes.NoteBox
import com.theagilemonkeys.notes.transactions.NoteUpdatedTransaction

class NoteUpdatedValidator : Validator<NoteUpdatedTransaction> {
    override fun validate(stateReader: SidechainStateReader, transaction: NoteUpdatedTransaction): Result<Unit> {
        var hasClosedNoteBoxAsInput = false

        transaction.fundingInputsIDs.forEach { id ->
            val potentialBox = stateReader.getClosedBox(id)
            if (!potentialBox.isPresent) {
                return Result.failure(IllegalArgumentException("Input box not closed or present"))
            }

            when (val box = potentialBox.get()) {
                is NoteBox -> {
                    hasClosedNoteBoxAsInput = true
                    if (box.data.title != transaction.data.title && box.data.id != transaction.data.id) {
                        return Result.failure(IllegalArgumentException("Note id or title changed on update"))
                    }
                    return@forEach
                }
                else -> {}
            }
        }

        if (!hasClosedNoteBoxAsInput) {
            return Result.failure(IllegalArgumentException("Input box of type NoteBox not found"))
        }

        return Result.success(Unit)
    }
}