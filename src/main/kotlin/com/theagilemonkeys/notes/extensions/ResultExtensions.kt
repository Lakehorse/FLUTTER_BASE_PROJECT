package com.theagilemonkeys.notes.extensions

fun <T> Result<T>.getOrThrow(): T = getOrNull() ?: run {
    throw exceptionOrNull() ?: Exception("Unkwnown Error")
}