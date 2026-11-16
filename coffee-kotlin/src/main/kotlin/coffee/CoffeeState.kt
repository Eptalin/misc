package org.example.coffee

import org.example.coffee.model.Coffee
import org.example.ui.*

// Data required for the CoffeeView UI State
data class CoffeeState(
    // Page header label
    val title: String = "Coffee Machine",

    // Message list with type flag
    val messages: List<UIMessage> = emptyList(),

    // Error list
    val errors: List<UIMessage> = emptyList(),

    // Options list
    val options: List<UIOption<*>> = emptyList(),

    // Flag to indicate coffee is being prepared
    val isPreparing: Boolean = false,

    // Coffee object
    val coffee: Coffee? = null
)