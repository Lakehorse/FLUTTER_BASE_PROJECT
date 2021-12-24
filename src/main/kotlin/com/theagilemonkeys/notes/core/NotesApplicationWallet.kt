
package com.theagilemonkeys.notes.core

import com.horizen.box.Box
import com.horizen.proposition.Proposition
import com.horizen.secret.Secret
import com.horizen.wallet.ApplicationWallet

class NotesApplicationWallet: ApplicationWallet {
    override fun onAddSecret(secret: Secret?) {}
    override fun onRemoveSecret(proposition: Proposition?) {}
    override fun onChangeBoxes(
        blockId: ByteArray?,
        boxesToUpdate: MutableList<Box<Proposition>>?,
        boxIdsToRemove: MutableList<ByteArray>?
    ) {
    }

    override fun onRollback(blockId: ByteArray?) {
    }
}