package com.example.fragment_application_24_2

class Income(val year: Int, val month: Int, val day: Int, val description: String, val amount: Double) {
    companion object {
        fun fromString(data: String): Income {
            val parts = data.split(",")
            return Income(parts[0].toInt(), parts[1].toInt(), parts[2].toInt(), parts[3], parts[4].toDouble())
        }

        fun toString(income: Income): String {
            return "${income.year},${income.month},${income.day},${income.description},${income.amount}"
        }
    }
}