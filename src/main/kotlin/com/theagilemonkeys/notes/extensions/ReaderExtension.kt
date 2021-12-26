
package com.theagilemonkeys.notes.extensions

import scorex.util.serialization.Reader
import java.nio.charset.StandardCharsets

fun Reader.string() = String(this.getBytes(this.int), StandardCharsets.UTF_8)
fun Reader.bytesMutableList(bytesSize: Int): MutableList<ByteArray> {
    val list = mutableListOf<ByteArray>()
    for (i in 1..int) {
        list.add(getBytes(bytesSize))
    }
    return list
}