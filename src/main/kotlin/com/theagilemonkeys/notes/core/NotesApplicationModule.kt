
package com.theagilemonkeys.notes.core

import com.google.inject.AbstractModule
import com.google.inject.TypeLiteral
import com.google.inject.name.Names
import com.horizen.SidechainSettings
import com.horizen.api.http.ApplicationApiGroup
import com.horizen.box.Box
import com.horizen.box.BoxSerializer
import com.horizen.companion.SidechainTransactionsCompanion
import com.horizen.proposition.Proposition
import com.horizen.secret.Secret
import com.horizen.secret.SecretSerializer
import com.horizen.settings.SettingsReader
import com.horizen.state.ApplicationState
import com.horizen.storage.Storage
import com.horizen.storage.leveldb.VersionedLevelDbStorageAdapter
import com.horizen.transaction.BoxTransaction
import com.horizen.transaction.TransactionSerializer
import com.horizen.utils.Pair
import com.horizen.wallet.ApplicationWallet
import com.theagilemonkeys.notes.api.NotesAPI
import com.theagilemonkeys.notes.boxes.NotesAppBoxes
import com.theagilemonkeys.notes.boxes.serializers.NoteBoxSerializer
import com.theagilemonkeys.notes.transactions.NotesAppTransactions
import com.theagilemonkeys.notes.transactions.serializers.NoteCreatedTransactionSerializer
import com.theagilemonkeys.notes.transactions.serializers.NoteDeletedTransactionSerializer
import com.theagilemonkeys.notes.transactions.serializers.NoteUpdatedTransactionSerializer
import java.io.File
import java.util.*

class NotesApplicationModule(userSettingsFileName: String?) : AbstractModule() {
    private val settingsReader: SettingsReader

    init {
        settingsReader = SettingsReader(userSettingsFileName, Optional.empty())
    }

    override fun configure() {
        // Get sidechain settings
        val sidechainSettings: SidechainSettings = settingsReader.sidechainSettings

        // Define custom serializers:

        // Specify how to serialize custom Boxes.
        // The hash map expect to have unique Box type ids as the keys.
        val customBoxSerializers: HashMap<Byte, BoxSerializer<Box<Proposition>>> =
            HashMap<Byte, BoxSerializer<Box<Proposition>>>()
        customBoxSerializers[NotesAppBoxes.NoteBox.id] = NoteBoxSerializer() as BoxSerializer<Box<Proposition>>

        // No custom secrets for CarRegistry app.
        val customSecretSerializers: HashMap<Byte, SecretSerializer<Secret>> = HashMap<Byte, SecretSerializer<Secret>>()

        // Specify how to serialize custom Transaction.
        val customTransactionSerializers: HashMap<Byte, TransactionSerializer<BoxTransaction<Proposition, Box<Proposition>>>> =
            HashMap<Byte, TransactionSerializer<BoxTransaction<Proposition, Box<Proposition>>>>()
        customTransactionSerializers[NotesAppTransactions.NoteCreated.id] =
            NoteCreatedTransactionSerializer() as TransactionSerializer<BoxTransaction<Proposition, Box<Proposition>>>
        customTransactionSerializers[NotesAppTransactions.NoteUpdated.id] =
            NoteUpdatedTransactionSerializer() as TransactionSerializer<BoxTransaction<Proposition, Box<Proposition>>>
        customTransactionSerializers[NotesAppTransactions.NoteDeleted.id] =
            NoteDeletedTransactionSerializer() as TransactionSerializer<BoxTransaction<Proposition, Box<Proposition>>>

        // Create companions that will allow to serialize and deserialize any kind of core and custom types specified.
        val transactionsCompanion = SidechainTransactionsCompanion(customTransactionSerializers)


        // Define Application state and wallet logic:
        val defaultApplicationWallet: ApplicationWallet = NotesApplicationWallet()
        val defaultApplicationState: ApplicationState = NotesApplicationState()

        // Define the path to storages:
        val dataDirPath: String = sidechainSettings.scorexSettings().dataDir().absolutePath
        val secretStore = File("$dataDirPath/secret")
        val walletBoxStore = File("$dataDirPath/wallet")
        val walletTransactionStore = File("$dataDirPath/walletTransaction")
        val walletForgingBoxesInfoStorage = File("$dataDirPath/walletForgingStake")
        val walletCswDataStorage = File("$dataDirPath/walletCswDataStorage")
        val stateStore = File("$dataDirPath/state")
        val stateForgerBoxStore = File("$dataDirPath/stateForgerBox")
        val stateUtxoMerkleTreeStore = File("$dataDirPath/stateUtxoMerkleTree")
        val historyStore = File("$dataDirPath/history")
        val consensusStore = File("$dataDirPath/consensusData")

