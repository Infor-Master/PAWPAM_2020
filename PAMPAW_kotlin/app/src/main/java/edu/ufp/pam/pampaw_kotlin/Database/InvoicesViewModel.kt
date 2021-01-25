package edu.ufp.pam.pampaw_kotlin.Database

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class InvoicesViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: InvoicesRepository

    // Use LiveData and cache customers returned by repository:
    //  - Put an observer on data fr UI to receive updates (instead of polling for changes).
    //  - The ViewModel separates the UI from the Repository.
    var allInvoices: LiveData<List<Invoice>>

    init {
        val invoiceDAO =
            InvoicesDatabase.getInvoiceDatabaseInstance(app, viewModelScope).invoiceDao()
        repository = InvoicesRepository(invoiceDAO)
        allInvoices = repository.allInvoices
    }

    /** Launch new (non-blocking) coroutine to insert a customer */
    fun insert(invoice: Invoice) =
        viewModelScope.launch(Dispatchers.IO) {
            Log.e(
                this.javaClass.simpleName,
                "launch(): async insert new customer ${invoice}"
            )
            repository.insertInvoice(invoice)
        }

    private val invoices: MutableLiveData<List<Invoice>> by lazy {
        MutableLiveData<List<Invoice>>().also {
            loadInvoices()
        }
    }

    fun getInvoices(): LiveData<List<Invoice>> {
        return invoices
    }

    /** Do an asynchronous operation to fetch customers */
    private fun loadInvoices() {
        Log.e(
            this.javaClass.simpleName,
            "loadUsers(): going to load customer to DB..."
        )
        /* 1. Execute and AsynTask... deprecated! */
        //val existingCustomers: List<Customer> = LoadCustomersFromDatabaseAsyncTask().execute("").get()
        //customers.postValue(existingCustomers)

        /* 2. Create a new coroutine to move the execution off the UI thread */
        viewModelScope.launch(Dispatchers.IO) {
            Log.e(
                this.javaClass.simpleName,
                "launch(): loading all customers..."
            )
            val invoiceDAO: InvoiceDAO = LoaderInvoicesContentDatabase.getInvoiceDao()
            allInvoices = invoiceDAO.loadAllInvoices()
        }
    }
}