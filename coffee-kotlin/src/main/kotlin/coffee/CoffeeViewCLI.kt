package org.example.coffee

import org.example.ui.UIMessage

class CoffeeViewCLI(
    private val viewModel: CoffeeViewModel,
) {
    private var header: String = ""

    // Retrieve the UI state, render it, and return user input.
    fun run() {
        while (true) {
            val state = viewModel.state.value
            render(state)
            val input = readlnOrNull()
            viewModel.handleInput(input)
        }
    }

    // Create UI based on the current state
    fun render(state: CoffeeState) {
        // Title
        header = state.title
        printHeader()

        // Errors if they exist
        if (state.errors.isNotEmpty()) {
            reportErrors(state.errors)
        }

        // Messages if they exist
        if (state.messages.isNotEmpty()) {
            for ((text) in state.messages) {
                println(text)
            }
            println()
        }

        // Options if they exist
        if (state.options.isNotEmpty()) {
            for ((label) in state.options) {
                println("- $label")
            }
            println()
        }

        // Preparing animation
        if (state.isPreparing) {
            repeat(3) {
                print(". ")
                Thread.sleep(1000)
            }
            print("Done!\n\nPress Enter to continue.")
        }

        // Present coffee
        if (state.coffee != null) {
            println("Enjoy your ${state.coffee.description()}.\n")
            print("\nPress Ctrl^C to exit the program.")
        }

        // User input prompt
        if (!state.isPreparing && state.coffee == null) {
            print("> ")
        }
    }

    // Display the header
    private fun printHeader() {
        // Clear terminal
        repeat(50) {
            print("\u001B[H\u001B[2J")
            System.out.flush()
        }
        // Print header
        repeat(50) { print("=") }
        println("\n")
        println("    $header \n")
        repeat(50) { print("=") }
        println("\n")
    }

    // Print the error screen
    private fun reportErrors(errors: List<UIMessage>) {
        for ((text) in errors) {
            println(text)
        }
        Thread.sleep(2500)
        printHeader()
    }
}