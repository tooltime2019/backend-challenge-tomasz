package com.tooltime.codingchallenge.repository

import com.tooltime.codingchallenge.invoice.InvoiceRecord
import com.tooltime.codingchallenge.invoice.InvoiceRepository
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.HashMap

@Repository
class InMemoryStore(
    private val db: HashMap<UUID, InvoiceRecord> = HashMap()
): InvoiceRepository {

    override fun CreateInvoice(invoice: InvoiceRecord): InvoiceRecord {
        db[invoice.id] = invoice
        return invoice
    }

    override fun GetInvoice(id: UUID): InvoiceRecord? {
        return db[id]
    }
}