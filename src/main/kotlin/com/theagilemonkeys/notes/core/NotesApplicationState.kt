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
import com