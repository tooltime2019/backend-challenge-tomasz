package com.tooltime.codingchallenge.invoice

import org.springframework.stereotype.Repository
import java.util.*

interface InvoiceRepository {
    fun CreateInvoice(invoice: InvoiceRecord): InvoiceRecord
    fun GetInvoice(id: UUID): InvoiceRecord?
}

data class InvoiceRecord (
    val id: UUID,
    val code: String,
    val title: String,
    val description: String?,
    val issuedAt: Date,
    val customer: Customer,
    val positions: List<Position>,
    val totalAmount: Double
)