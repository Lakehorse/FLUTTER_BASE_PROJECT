package com.theagilemonkeys.notes.extensions

import scorex.util.serialization.Writer

fun String.serialize(writer: Writer) {
    this.toByteArray().let {
        writer.pu