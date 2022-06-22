
import com.horizen.state.SidechainStateReader
import com.theagilemonkeys.notes.boxes.NoteBox
import com.theagilemonkeys.notes.transactions.NoteDeletedTransaction
import com.theagilemonkeys.notes.transactions.validators.Validator

class NoteDeletedValidator : Validator<NoteDeletedTransaction> {
    override fun validate(stateReader: SidechainStateReader, transaction: NoteDeletedTransaction): Result<Unit> {
        transaction.fundingInputsIDs.forEach { id ->
            val potentialBox = stateReader.getClosedBox(id)
            if (!potentialBox.isPresent) {
                return Result.failure(IllegalArgumentException("Input box not closed or present"))
            }

            when (potentialBox.get()) {
                is NoteBox -> {
                    return Result.success(Unit)
                }
                else -> {}
            }
        }

        return Result.failure(IllegalArgumentException("Input box of type NoteBox not found"))
    }
}