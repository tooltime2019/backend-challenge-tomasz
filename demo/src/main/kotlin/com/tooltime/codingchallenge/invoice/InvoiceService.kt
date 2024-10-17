package com.tooltime.codingchallenge.invoice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class InvoiceService(
    @Autowired private val repository: InvoiceRepository
) {
    fun CreateInvoice(invoice: Invoice): Invoice {
        val invoiceRecord = toInvoiceRecord(invoice)
        val createdRecord = repository.CreateInvoice(invoiceRecord)
        return toInvoice(createdRecord)
    }

    fun GetInvoice(idStr: String): Invoice? {
        val id = UUID.fromString(idStr)
        val invoiceRecord = repository.GetInvoice(id)
        return if (invoiceRecord != null) {
            toInvoice(invoiceRecord)
        } else {
            null
        }
    }
}

private fun toInvoiceRecord(invoice: Invoice): InvoiceRecord {
    val totalAmount = invoice.computeTotalAmount()
    return InvoiceRecord (
        id = UUID.randomUUID(),
        code = invoice.code,
        title = invoice.title,
        description = invoice.description,
        issuedAt = invoice.issuedAt,
        customer = invoice.customer,
        positions = invoice.positions,
        totalAmount = totalAmount
    )
}

private fun toInvoice(invoiceRecord: InvoiceRecord): Invoice {
    return Invoice (
        id = invoiceRecord.id,
        code = invoiceRecord.code,
        title = invoiceRecord.title,
        description = invoiceRecord.description,
        issuedAt = invoiceRecord.issuedAt,
        customer = invoiceRecord.customer,
        positions = invoiceRecord.positions,
        totalAmount = invoiceRecord.totalAmount
    )
}