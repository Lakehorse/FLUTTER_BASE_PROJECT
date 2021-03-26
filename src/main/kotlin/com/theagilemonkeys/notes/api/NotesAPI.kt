
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