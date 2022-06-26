package com.theagilemonkeys.notes.transactions.validators

import com.horizen.state.SidechainStateReader
import com.horizen.transaction.AbstractRegularTransaction

interface Validator<T: AbstractRegularTransaction> {
    fun validate(stateReader: SidechainStateReader, transaction: T): Result<Unit>
}