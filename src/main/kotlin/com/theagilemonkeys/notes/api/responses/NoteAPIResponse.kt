package com.theagilemonkeys.notes.api.responses

import com.horizen.api.http.SuccessResponse

interface NoteAPIResponse: SuccessResponse {
    val transactionBytes: String
}