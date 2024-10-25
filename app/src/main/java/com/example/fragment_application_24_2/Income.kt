package com.example.fragment_application_24_2

data class Income(
    val date: String,
    val description: String,
    val amount: Double
) {
    companion object {
        fun fromString(incomeString: String): Income {
            val parts = incomeString.split(",")
            return Income(parts[0], parts[1], parts[2].toDouble())
        }
    }
}