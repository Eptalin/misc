package org.example.coffee

import org.example.ui.MessageType

class CoffeeViewCLI(
    private val viewModel: CoffeeViewModel,
) {
    // Consume UI state, render it, and return user input.
    fun run() {
        while (true) {
            val state = viewModel.state.value
            render(state)
            break
        }
    }

    fun render(state: CoffeeState) {
        println(state.title)
        for ((text, type) in state.messages) {
            val type = when (type) {
                MessageType.ERROR -> "ERROR"
                MessageType.MESSAGE -> "MESSAGE"
                MessageType.QUESTION -> "QUESTION"
                MessageType.PROMPT -> "PROMPT"
            }
            println("$text ($type)")
        }
        for ((label) in state.options) {
            println("- $label")
        }
    }
}