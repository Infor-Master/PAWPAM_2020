package edu.ufp.pam.pampaw_kotlin.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import edu.ufp.pam.pampaw_kotlin.models.InvoiceInfo
import java.awt.print.Book


class BooksAdapter(context: Context, invoiceInfo: List<InvoiceInfo>) : BaseAdapter() {
    val invoiceInfo= invoiceInfo
    val mContext= context


    override fun getCount(): Int {
        return invoiceInfo.size
    }


    override fun getItemId(position: Int): Long {
        return 0
    }

   /* // 4
    override fun getItem(position: Int): Any {
        return null
    }*/

    // 5
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val InvoiceInfo invoiceinfo = in

        
        val dummyTextView = TextView(mContext)
        dummyTextView.text = position.toString()
        return dummyTextView
    }

}