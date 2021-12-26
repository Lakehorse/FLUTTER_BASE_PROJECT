package com.theagilemonkeys.notes.extensions

import scorex.util.serialization.Writer

fun Collection<ByteArray>.serialize(writer: Writer) {
    writer.putInt(size)
    forEach { writer.putBytes(it) }
}