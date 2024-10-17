package com.tooltime.codingchallenge.invoice

import java.util.Date
import java.util.UUID

data class Invoice (
    val id: UUID?,
    val code: String,
    val title: String,
    val description: String?,
    val issuedAt: Date,
    val customer: Customer,
    val positions: List<Position>,
    val totalAmount: Double?
){
    fun computeTotalAmount(): Double {
        var total = 0.0
        positions.forEach{
            total += it.amount
        }
        return total
    }
}

data class Customer (
    val id: UUID,
    val name: String,
    val address: String,
)

data class Position (
    val description: String,
    val amount: Double,
)