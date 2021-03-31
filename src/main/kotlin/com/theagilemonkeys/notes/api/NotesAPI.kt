
package com.theagilemonkeys.notes.api

import akka.http.javadsl.server.Route
import com.horizen.api.http.ApiResponse
import com.horizen.api.http.ApplicationApiGroup
import com.horizen.box.Box
import com.horizen.companion.SidechainTransactionsCompanion
import com.horizen.node.SidechainNodeView
import com.horizen.proposition.Proposition
import com.horizen.proposition.PublicKey25519PropositionSerializer
import com.horizen.utils.BytesUtils
import com.theagilemonkeys.notes.api.requests.CreateNoteRequest
import com.theagilemonkeys.notes.api.requests.DeleteNoteRequest
import com.theagilemonkeys.notes.api.requests.UpdateNoteRequest
import com.theagilemonkeys.notes.api.responses.CreateNoteResponse
import com.theagilemonkeys.notes.api.responses.DeleteNoteResponse
import com.theagilemonkeys.notes.api.responses.ErrorResponse
import com.theagilemonkeys.notes.api.responses.UpdateNoteResponse
import com.theagilemonkeys.notes.boxes.NoteBox
import com.theagilemonkeys.notes.boxes.data.NoteBoxData
import com.theagilemonkeys.notes.transactions.NoteCreatedTransaction
import com.theagilemonkeys.notes.transactions.NoteDeletedTransaction
import com.theagilemonkeys.notes.transactions.NoteUpdatedTransaction
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils
import scala.sys.Prop
import java.util.*

private object NotesAPIErrors {
    val ownerPropositionError = ErrorResponse("Owner proposition not owned by the node")
    val noteBoxNotFound = { id: String ->
        ErrorResponse("Box with id $id not found")
    }
}

class NotesAPI(private val sidechainTransactionsCompanion: SidechainTransactionsCompanion) : ApplicationApiGroup() {
    override fun basePath(): String = "notes"

    override fun getRoutes(): MutableList<Route> = mutableListOf(
        bindPostRequest("createNote", this::createNote, CreateNoteRequest::class.java),
        bindPostRequest("updateNote", this::updateNote, UpdateNoteRequest::class.java),
        bindPostRequest("deleteNote", this::deleteNote, DeleteNoteRequest::class.java)
    )

    private fun createNote(view: SidechainNodeView, request: CreateNoteRequest): ApiResponse {
        val noteProposition = PublicKey25519PropositionSerializer.getSerializer()
            .parseBytes(BytesUtils.fromHexString(request.proposition))

        val data = NoteBoxData(
            noteProposition,
            UUID.randomUUID().toString(),
            request.title,
            request.content,
            view.nodeHistory.currentHeight.toLong()
        )
        val boxes = getTransactionFundingBoxes(view, request.fee)

        val signedTransaction = view.createSignedTransaction(boxes.fundingInputs) { proofs ->
            NoteCreatedTransaction(
                boxes.fundingInputs.map { it.id() }.toMutableList(),
                proofs,
                boxes.changeOutput,
                request.fee,