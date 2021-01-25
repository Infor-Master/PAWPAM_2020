package edu.ufp.pam.pampaw_kotlin.Database

import androidx.room.*

/**
 * A biblioteca de persistência Room oferece uma camada de abstração sobre o SQLite
 * para permitir um acesso robusto à BD.
 */
@Entity(
    tableName = "invoices",
    indices = [Index(value = ["invoiceID"])]
)
data class Invoice(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "invoiceID") var invoiceID: Int?,
    @ColumnInfo(name = "invoiceName") var invoiceName: String? = "",
    @ColumnInfo(name = "userID") var userID: Int?,
    @ColumnInfo(name = "info") var info: String? = "",
    @ColumnInfo(name = "image") var image: String? = ""
) {
    constructor() : this(0, "", 0, "","")
}