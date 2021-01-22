
package com.theagilemonkeys.notes

import com.google.inject.Guice
import com.horizen.SidechainApp
import com.theagilemonkeys.notes.core.NotesApplicationModule
import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Please provide settings file name as first parameter!")
        return
    }
    if (!File(args[0]).exists()) {
        println("File on path " + args[0] + " doesn't exist")
        return
    }
    val settingsFileName = args[0]

    // To Initialize the core starting point - SidechainApp, Guice DI is used.
    // Note: it's possible to initialize SidechainApp both using Guice DI or directly by emitting the constructor.
    val injector = Guice.createInjector(NotesApplicationModule(settingsFileName))
    val sidechainApp = injector.getInstance(SidechainApp::class.java)

    // Start the car registry sidechain node.
    sidechainApp.run()
    println("Notes Sidechain application successfully started...")
}