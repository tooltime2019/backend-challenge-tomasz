package com.tooltime.codingchallenge.api

import com.tooltime.codingchallenge.invoice.InvoiceService
import com.tooltime.codingchallenge.invoice.Position
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping("/invoices")
class InvoiceController(@Autowired private val invoiceService: InvoiceService) {

    @PostMapping
    fun createInvoice(@RequestBody invoice: CreateInvoice): ResponseEntity<Invoice> {
        val createdInvoice = invoiceService.CreateInvoice(toServiceInvoice(invoice))
        return ResponseEntity(toAPIInvoice(createdInvoice), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getInvoice(@PathVariable("id") id: String): ResponseEntity<Invoice> {
        val invoice = invoiceService.GetInvoice(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found")
        return ResponseEntity(toAPIInvoice(invoice), HttpStatus.OK)
    }

}

data class Invoice(
    val id: String,
    val code: String,
    val title: String,
    val description: String?,
    val issuedAt: Date,
    val customer: Customer,
    val positions: List<InvoicePosition>,
    val totalAmount: Double
)

data class CreateInvoice(
    val code: String,
    val title: String,
    val description: String?,
    val issuedAt: Date,
    val customer: Customer,
    val positions: List<InvoicePosition>,
)

data class Customer (
    val id: UUID,
    val name: String,
    val address: String,
)

data class InvoicePosition (
    val description: String,
    val amount: Double,
)

private fun toServiceInvoice(invoice: CreateInvoice): com.tooltime.codingchallenge.invoice.Invoice {
    val positions = invoice.positions.map {
        Position(
            description = it.description,
            amount = it.amount
        )
    }
    return com.tooltime.codingchallenge.invoice.Invoice(
        code = invoice.code,
        title = invoice.title,
        description = invoice.description,
        issuedAt = invoice.issuedAt,
        customer = com.tooltime.codingchallenge.invoice.Customer(
            id = invoice.customer.id,
            name = invoice.customer.name,
            address = invoice.customer.address,
        ),
        positions = positions,
        id = null,
        totalAmount = null,
    )
}

private fun toAPIInvoice(invoice: com.tooltime.codingchallenge.invoice.Invoice): Invoice {
    val positions = invoice.positions.map {
        InvoicePosition(
            description = it.description,
            amount = it.amount
        )
    }

    return Invoice(
        id = (invoice.id ?: "").toString(),
        code = invoice.code,
        title = invoice.title,
        description = invoice.description,
        issuedAt = invoice.issuedAt,
        customer = Customer(
            id = invoice.customer.id,
            name = invoice.customer.name,
            address = invoice.customer.address,
        ),
        positions = positions,
        totalAmount = invoice.totalAmount ?: 0.0,
    )
}