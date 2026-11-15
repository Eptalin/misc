package org.example.coffee

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.coffee.model.CoffeeType
import org.example.coffee.model.MilkType
import org.example.ui.MessageType
import org.example.ui.UIMessage
import org.example.ui.UIOption

class CoffeeViewModel {

    // Initial UI State
    private val _state = MutableStateFlow(
        CoffeeState(
            title = "Coffee Machine",
            messages = listOf(
                UIMessage("How do you take your coffee?", MessageType.QUESTION),
            ),
            options = listOf(
                UIOption("Black", CoffeeType.BLACK),
                UIOption("White", CoffeeType.WHITE),
            )
        )
    )

    // Expose state to consumers
    val state: StateFlow<CoffeeState> = _state.asStateFlow()

    // Info to create coffee
    private var coffeeType: CoffeeType? = null
    private var milkType = MilkType.NONE
    private var sugars = 0

    // Track which step of the process we're up to
    private var step = ProcessStep.COFFEE_TYPE

    fun handleInput(rawInput: String) {
        val input = rawInput.trim().uppercase().replace(" ", "_")
        when (step) {

            ProcessStep.COFFEE_TYPE -> {
                val type = CoffeeType.entries.find { it.name == input }
                if (type == null) {
                    appendError("Invalid input.")
                    return
                }
                coffeeType = type
                if (type == CoffeeType.WHITE) {
                    step = ProcessStep.MILK_TYPE
                    setMilkTypeState()
                }
                if (type == CoffeeType.BLACK) {
                    step = ProcessStep.SUGAR
                }
            }

            ProcessStep.MILK_TYPE -> {
                val type = MilkType.entries.find { it.name == input }
                if (type == null) {
                    appendError("Not a valid milk type.")
                    return
                }
            }

            ProcessStep.SUGAR -> {
                val intInput = input.toIntOrNull()
                if (intInput == null || intInput <= 0) {
                    appendError("Please enter a positive integer.")
                    return
                }
                sugars = intInput
                step = ProcessStep.COMPLETE
            }

            ProcessStep.COMPLETE -> TODO()
        }
    }

    private fun setMilkTypeState() {
        _state.update { CoffeeState(
            messages = listOf(
                UIMessage("What kind of milk would you like?", MessageType.QUESTION),
            ),
            options = listOf(
                UIOption("Full Cream", MilkType.FULL_CREAM),
                UIOption("Low Fat", MilkType.LOW_FAT),
                UIOption("Almond", MilkType.ALMOND),
                UIOption("Soy", MilkType.SOY),
            )
        ) }
    }

    private fun appendError(message: String) {
        _state.update { current ->
            current.copy(
                messages = state.value.messages + UIMessage(message, MessageType.ERROR)
            )
        }
    }

}