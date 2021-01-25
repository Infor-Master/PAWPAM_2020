package edu.ufp.pam.pampaw_kotlin.Database

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class InvoicesRepository(private val invoiceDAO: InvoiceDAO) {

    // Room executes all queries on a separate thread.
    // The public property is an observable LiveData which notifies observer when  data changes.
    val allInvoices: LiveData<List<Invoice>> = invoiceDAO.loadAllInvoices()

    // Make this a suspend function so the caller knows this must be called on a non-UI thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertInvoice(invoice: Invoice) {
        Log.e(
            this.javaClass.simpleName,
            "insertInvoice(): going to insert new invoice ${invoice}"
        )
        invoiceDAO.insertInvoice(invoice)
    }
}