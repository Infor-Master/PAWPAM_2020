package edu.ufp.pam.pampaw_kotlin.retrofit

import android.content.Context
import com.auth0.android.jwt.JWT
import edu.ufp.pam.pampaw_kotlin.models.*
import edu.ufp.pam.pampaw_kotlin.store.Global
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
        println(Global.token)
        retrofit.addInvoice( "Bearer ${Global.token}",invoiceData).enqueue(
            object : Callback<InvoiceInfo> {
                override fun onFailure(call: Call<InvoiceInfo>, t: Throwable) {
                    println(" ERROR CAUSE " + t.message)
                    onResult(null)
                }
                override fun onResponse( call: Call<InvoiceInfo>, response: Response<InvoiceInfo>) {
                    val addedInvoice = response.body()
                    println(addedInvoice)
                    onResult(addedInvoice)
                }
            }
        )
    }


    fun getUserInvoices(onResult: (ListInvoices?) -> Unit){

        val jwt = JWT(Global.token)
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)

        retrofit.getUserInvoices("Bearer ${Global.token}",jwt.getClaim("id").asInt()!!).enqueue(
            object : Callback<ListInvoices> {
                override fun onResponse( call: Call<ListInvoices>, response: Response<ListInvoices>) {
                    if(response.isSuccessful){
                        val listInvoices = response.body()
                        onResult(listInvoices)
                    }
                }

                override fun onFailure(call: Call<ListInvoices>, t: Throwable) {
                    println(" ERROR CAUSE " + t.message)
                    onResult(null)
                }
            }
        )
    }

    fun deleteInvoice(invoiceID: Int, onResult: (MessageRetorned?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)

        retrofit.deleteInvoice("Bearer ${Global.token}",invoiceID).enqueue(
            object : Callback<MessageRetorned> {
                override fun onFailure(call: Call<MessageRetorned>, t: Throwable) {
                    println(" ERROR CAUSE " + t.message)
                    onResult(null)
                }

                override fun onResponse(call: Call<MessageRetorned>, response: Response<MessageRetorned>) {
                    val messageRetorned = response.body()
                    onResult(messageRetorned)
                }
            }
        )
    }

    fun updateProfile(profileInfo: Profile, onResult: (MessageRetorned?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.updateProfile( "Bearer ${Global.token}",profileInfo).enqueue(

            object : Callback<MessageRetorned> {
                override fun onFailure(call: Call<MessageRetorned>, t: Throwable) {
                    println(" ERROR CAUSE " + t.message)
                    onResult(null)
                }

                override fun onResponse(call: Call<MessageRetorned>, response: Response<MessageRetorned>) {
                    val updatedProfile = response.body()
                    println(updatedProfile)
                    onResult(updatedProfile)
                }
            }
        )
    }
}