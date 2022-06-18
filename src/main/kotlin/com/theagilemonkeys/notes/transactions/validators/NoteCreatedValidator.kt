import com.horizen.state.SidechainStateReader
import com.theagilemonkeys.notes.transactions.NoteCreatedTransaction
import com.theagilemonkeys.notes.transactions.validators.Validator


class NoteCreatedValidator : Validator<NoteCreatedTransaction> {
    // TODO: Find a way to check if a note exist by internal ID.
    override fun validate(stateReader: Sidechai