package edu.ufp.pam.pampaw_kotlin.retrofit

import edu.ufp.pam.pampaw_kotlin.models.LoginInfo
import edu.ufp.pam.pampaw_kotlin.models.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {

    fun addUser(userData: UserInfo, onResult: (UserInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)

        println("URL " + ServiceBuilder.retrofit.baseUrl().toString())

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
                    println(loginUser)

                    onResult(loginUser)
                }
            }
        )
    }
}