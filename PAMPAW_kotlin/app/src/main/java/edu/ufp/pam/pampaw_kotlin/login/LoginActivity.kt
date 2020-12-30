package edu.ufp.pam.pampaw_kotlin.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import edu.ufp.pam.pampaw_kotlin.CaptureImage.CaptureImageActivity
import edu.ufp.pam.pampaw_kotlin.R
import edu.ufp.pam.pampaw_kotlin.models.LoginInfo
import edu.ufp.pam.pampaw_kotlin.retrofit.RestApiService
import edu.ufp.pam.pampaw_kotlin.signup.SignupActivity
import edu.ufp.pam.pampaw_kotlin.store.Global
import edu.ufp.pam.pampaw_kotlin.store.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login.setOnClickListener{
            loginUser()
        }

        button_goSignUp.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

    }

    fun loginUser() {

        val apiService = RestApiService()

        val loginInfo = LoginInfo(

            username = (findViewById<EditText>(R.id.editTextLoginUsername)).text.toString(),
            password = (findViewById<EditText>(R.id.editTextLoginPassword)).text.toString(),
            token = null
        )

        apiService.loginUser(loginInfo) {
            val auxToken= it?.token
            if(auxToken!=null){
                Global.token+=loginInfo.token

                SharedPreferencesHelper(this).setValueString(
                    "token",
                    auxToken
                )

                val intent = Intent(this, CaptureImageActivity::class.java)
                startActivity(intent)
            }
        }
    }
}