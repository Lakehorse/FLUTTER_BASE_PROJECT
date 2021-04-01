package com.theagilemonkeys.notes.api.requests

interface BaseRequest {
    // Hex representation
    val proposition: String
    val fee: Long
}