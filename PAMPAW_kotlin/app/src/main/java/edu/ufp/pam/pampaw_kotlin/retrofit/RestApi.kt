package edu.ufp.pam.pampaw_kotlin.retrofit

import edu.ufp.pam.pampaw_kotlin.models.InvoiceInfo
import edu.ufp.pam.pampaw_kotlin.models.LoginInfo
import edu.ufp.pam.pampaw_kotlin.models.UserInfo
import edu.ufp.pam.pampaw_kotlin.store.Global
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface RestApi {

    @Headers("Content-Type: application/json")

    @POST("signup")
    fun addUser(@Body userData: UserInfo): Call<UserInfo>

    @POST("login")
    fun loginUser(@Body userData: LoginInfo): Call<LoginInfo>

    @POST("user/invoices")
    fun addInvoice(@Header("Authorization: ") authorization:String, @Body invoiceData: InvoiceInfo): Call<InvoiceInfo>
}