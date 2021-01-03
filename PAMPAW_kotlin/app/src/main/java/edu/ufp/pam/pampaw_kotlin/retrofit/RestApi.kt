package edu.ufp.pam.pampaw_kotlin.retrofit

import edu.ufp.pam.pampaw_kotlin.models.*
import edu.ufp.pam.pampaw_kotlin.store.Global
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RestApi {

    @Headers("Content-Type: application/json")

    @POST("signup")
    fun addUser(@Body userData: UserInfo): Call<UserInfo>

    @POST("login")
    fun loginUser(@Body userData: LoginInfo): Call<LoginInfo>

    @POST("user/invoices")
    fun addInvoice(@Header("Authorization") token:String, @Body invoiceData: InvoiceInfo): Call<InvoiceInfo>

    @GET("/api/invoices/user/{Id}")
    fun getUserInvoices(@Header("Authorization") token:String, @Path("Id") userId: Int): Call<ListInvoices>

    @DELETE("/api/user/invoices/{Id}")
    fun deleteInvoice(@Header("Authorization") token:String, @Path("Id") userId: Int): Call<MessageRetorned>

    @POST("/api/user/update")
    fun updateProfile(@Header("Authorization") token:String,@Body invoiceData: Profile): Call<MessageRetorned>
}