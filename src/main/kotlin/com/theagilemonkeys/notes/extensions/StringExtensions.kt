package com.theagilemonkeys.notes.extensions

import scorex.util.serialization.Writer

fun String.serialize(writer: Writer) {
    this.toByteArray().let {
        writer.putInt(it.size)
        writer.putBytes(it)
    }
}