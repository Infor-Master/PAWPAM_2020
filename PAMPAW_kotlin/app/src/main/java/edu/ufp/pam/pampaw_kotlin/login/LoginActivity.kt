package edu.ufp.pam.pampaw_kotlin.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import edu.ufp.pam.pampaw_kotlin.R
import edu.ufp.pam.pampaw_kotlin.models.LoginInfo
import edu.ufp.pam.pampaw_kotlin.retrofit.RestApiService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login.setOnClickListener{
            loginUser()
        }
    }

    fun loginUser() {

        val apiService = RestApiService()

        val loginInfo = LoginInfo(

            username = (findViewById<EditText>(R.id.editTextLoginUsername)).text.toString(),
            password = (findViewById<EditText>(R.id.editTextLoginPassword)).text.toString()
        )

        apiService.loginUser(loginInfo) {
            println("Login")
        }
    }
}