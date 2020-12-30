package edu.ufp.pam.pampaw_kotlin.retrofit

import android.content.Context
import edu.ufp.pam.pampaw_kotlin.models.InvoiceInfo
import edu.ufp.pam.pampaw_kotlin.models.LoginInfo
import edu.ufp.pam.pampaw_kotlin.models.UserInfo
import edu.ufp.pam.pampaw_kotlin.store.Global
import edu.ufp.pam.pampaw_kotlin.store.SharedPreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {
    private lateinit var context: Context;

    // SignUp
    fun addUser(userData: UserInfo, onResult: (UserInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)

        retrofit.addUser(userData).enqueue(
            object : Callback<UserInfo> {
                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    println(" ERROR CAUSE " + t.message)
                    onResult(null)
                }
                override fun onResponse( call: Call<UserInfo>, response: Response<UserInfo>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }

    //Login
    fun loginUser(userData: LoginInfo, onResult: (LoginInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)

        retrofit.loginUser(userData).enqueue(
            object : Callback<LoginInfo> {
                override fun onFailure(call: Call<LoginInfo>, t: Throwable) {
                    println(" ERROR CAUSE " + t.message)
                    onResult(null)
                }
                override fun onResponse( call: Call<LoginInfo>, response: Response<LoginInfo>) {
                    val loginUser = response.body()
                    onResult(loginUser)
                }
            }
        )
    }

    // Invoice
    fun addInvoice(invoiceData: InvoiceInfo, onResult: (InvoiceInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)

        retrofit.addInvoice("Bearer ".plus(Global.token),invoiceData).enqueue(
            object : Callback<InvoiceInfo> {
                override fun onFailure(call: Call<InvoiceInfo>, t: Throwable) {
                    println(" ERROR CAUSE " + t.message)
                    onResult(null)
                }
                override fun onResponse( call: Call<InvoiceInfo>, response: Response<InvoiceInfo>) {
                    val addedInvoice = response.body()
                    onResult(addedInvoice)
                }
            }
        )
    }
}