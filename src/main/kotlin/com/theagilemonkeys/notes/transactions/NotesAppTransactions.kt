
package com.theagilemonkeys.notes.transactions

enum class NotesAppTransactions(val id: Byte) {
    NoteCreated(1.toByte()),
    NoteUpdated(2.toByte()),
    NoteDeleted(3.toByte())
}