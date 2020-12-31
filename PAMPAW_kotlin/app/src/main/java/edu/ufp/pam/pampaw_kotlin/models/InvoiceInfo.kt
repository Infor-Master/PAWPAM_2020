package edu.ufp.pam.pampaw_kotlin.models

import com.google.gson.annotations.SerializedName

data class InvoiceInfo (
    @SerializedName("Image") val image: String?,
    @SerializedName("Name") val name: String?,
    @SerializedName("UserID") val userid: Int?,
    @SerializedName("Info") val info: String?,
    @SerializedName("ID") val id: Int?

)

data class ListInvoices(
    @SerializedName("data") val data:  List<InvoiceInfo>?
)

data class MessageRetorned(
    @SerializedName("message") val message:  String?
)