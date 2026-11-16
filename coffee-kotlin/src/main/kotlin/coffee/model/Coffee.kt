package org.example.coffee.model

data class Coffee(
    val type: CoffeeType,
    val milkType: MilkType,
    val sugars: Int,
) {
    // Print a string description of the coffee
    fun description(): String {
        val coffee = "${type.name.lowercase()} coffee"
        val milk = if (milkType != MilkType.NONE) {
            " with ${milkType.name.replace("_", "-").lowercase()} milk"
        } else ""
        val sugarConnector = if (milkType == MilkType.NONE) " with" else " and"
        val sugarPlural = if (sugars == 1) "sugar" else "sugars"
        val sugar = if (sugars > 0) "$sugarConnector $sugars $sugarPlural" else ""

        return "$coffee$milk$sugar"
    }
}
