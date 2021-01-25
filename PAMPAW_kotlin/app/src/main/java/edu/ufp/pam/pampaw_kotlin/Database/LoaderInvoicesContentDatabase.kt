package edu.ufp.pam.pampaw_kotlin.Database

import android.content.Context
import android.util.Log
import java.util.ArrayList
import java.util.HashMap

@Suppress("JAVA_CLASS_ON_COMPANION")
class LoaderInvoicesContentDatabase {

    companion object Factory {
        private lateinit var invoiceDAO: InvoiceDAO
        //private lateinit var customerTaskDao: CustomerTaskDao
        //private lateinit var customerTaskDetailViewDao: CustomerTaskDetailViewDao
        private lateinit var db: InvoicesDatabase //Database

        private var _INVOICE_ITEMS: MutableList<InvoiceItem> = ArrayList()
        val INVOICE_ITEMS: MutableList<InvoiceItem>
            get() {
                /*if (_CUSTOMER_ITEMS == null) {
                    _CUSTOMER_ITEMS = ArrayList() // Type parameters are inferred
                }*/
                return _INVOICE_ITEMS
            }

        private var _INVOICE_ITEM_MAP: MutableMap<String, InvoiceItem> = HashMap()
        val INVOICE_ITEM_MAP: MutableMap<String, InvoiceItem>
            get() {
                /*if (_CUSTOMER_ITEM_MAP == null) {
                    _CUSTOMER_ITEM_MAP = HashMap() // Type parameters are inferred
                }*/
                return _INVOICE_ITEM_MAP
            }

        fun getInvoiceDao() : InvoiceDAO{
            return invoiceDAO
        }

        /**
         * Create the database by getting its INSTANCE
         */
        fun createDb(context: Context) {
            Log.e(
                this.javaClass.simpleName,
                "createDb(): going to create DB and get customerDao..."
            )
            //Create a version of the DB
            db = InvoicesDatabase.getInvoiceDatabaseInstance(context)
            invoiceDAO = db.invoiceDao()
        }

        /**
         * Close database
         */
        fun closeDb() {
            Log.e(this.javaClass.simpleName, "closeDb(): going to close DB...")
            db.close()
        }


        private fun addItem(item: InvoiceItem) {
            INVOICE_ITEMS.add(item)
            INVOICE_ITEM_MAP.put(item.id, item)
        }

        private fun createInvoiceItem(c: Invoice): InvoiceItem {
            return InvoiceItem("${c.invoiceID}", c.invoiceName, makeDetails(c))
        }

        private fun makeDetails(c: Invoice): String {
            val builder = StringBuilder()
            builder.append(c.invoiceID).append("\n")
            builder.append(c.invoiceName).append("\n")
            builder.append(c.info).append("\n")
            builder.append(c.userID)
            return builder.toString()
        }
    }

    /**
     * A CustomerItem item representing a piece of content from Customer.
     */
    data class InvoiceItem(val id: String, val content: String?, val details: String) {
        override fun toString(): String = if (content!=null) content else ""
    }
}