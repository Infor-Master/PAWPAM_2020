package edu.ufp.pam.pampaw_kotlin.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable

@Dao
interface InvoiceDAO {

    /**
     * Get all invoices.
     * @return all invoices from the table.
     */
    @Query("SELECT * FROM invoices")
    fun loadAllInvoices(): LiveData<List<Invoice>>

    /**
     * Get a invoice by id.
     * @return the invoice from the table with a specific id.
     */
    @Query("SELECT * FROM invoices WHERE invoiceID = :id LIMIT 1")
    fun getInvoiceById(id: String): Invoice

    /**
     * Insert a invoice in the database (returns id), replace it if already exists.
     * @param invoice the invoice to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvoice(invoice: Invoice): Long

    /**
     * Insert a invoice in the database (returns id), replace it if already exists.
     * @param invoice the invoice to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvoiceCompletable(invoice: Invoice): Completable

    /**
     * Insert 1+ invoices into database. If the invoices already exists, replace them.
     * @param invoices the set of invoices to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvoices(vararg invoices: Invoice): List<Long>

    /**
     * Update 1+ invoices into database (returns number of updates rows).
     * @param invoices the set of invoices to be updated.
     */
    @Update
    fun updateInvoices(vararg invoices: Invoice): Int

    /**
     * Delete 1+ invoices from database (returns number of deleted rows).
     * @param invoices the set of invoices to be deleted.
     */
    @Delete
    fun deleteInvoices(vararg invoices: Invoice): Int

    /**
     * Delete all invoices.
     */
    @Query("DELETE FROM invoices")
    fun deleteAllInvoices(): Int
}