package com.example.fingrow.ui.home.cards

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class GoalCard(title: String, amount: Double, month: Int, year: Int, currAmount: Double = 0.0) {
    private val myTitle = title
    private val endMonth = month
    private val endYear = year
    private val currentAmount = currAmount
    private val totalAmount = amount

    fun getTitle(): String {
        return myTitle
    }

    fun isFlexible(): Boolean = (endMonth == -1 || endYear == -1)

    fun getTimeLeft(): String {
        val monthsAway = monthsAway()
        if (monthsAway == 1) {
            return "less than 1 month"
        } else if (monthsAway < 60) {
            return "$monthsAway months"
        }

        return "${monthsAway / 12} years"
    }

    fun getSavingsNeeded(): String {
        val monthsAway = monthsAway()
        val amountNeeded: Double
        val formattedAmount: String
        if (monthsAway < 60) {
            amountNeeded = (totalAmount - currentAmount) / monthsAway
            formattedAmount = String.format("%,.2f", amountNeeded)
            return "$formattedAmount per month"
        }

        amountNeeded = (totalAmount - currentAmount) / (monthsAway / 12)
        formattedAmount = String.format("%,.2f", amountNeeded)
        return "$formattedAmount per year"
    }

    fun getCurrentAmount(): String = String.format("%,d", currentAmount.roundToInt())

    fun getTotalAmount(): String = String.format("%,d", totalAmount.roundToInt())

    fun getProgressPercent(): Int {
        return (100 * (currentAmount / totalAmount)).roundToInt()
    }

    fun getEndDate(): String {
        val c = Calendar.getInstance()
        c.set(endYear, endMonth - 1, 1)
        return SimpleDateFormat.getDateInstance().format(c.time)
    }

    private fun monthsAway(): Int {
        val currYear = Calendar.getInstance().get(Calendar.YEAR)
        val currMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        return (endYear - currYear) * 12 + (endMonth - currMonth)
    }
}