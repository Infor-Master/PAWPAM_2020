package edu.ufp.pam.pampaw_kotlin.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.auth0.android.jwt.JWT
import edu.ufp.pam.pampaw_kotlin.CaptureImage.GalleryImageActivity
import edu.ufp.pam.pampaw_kotlin.HomePage.HomePageActivity
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
            println(it?.token)
            val auxToken= it?.token
            if(auxToken!=null){

                var auxjwt = JWT(auxToken)
                println("----------------TOKEN-----------------")
                //println(auxjwt.claims.toString())
                println(auxjwt.getClaim("name").asString())
                println(auxjwt.getClaim("NIF").asInt())
                println(auxjwt.getClaim("username").asString())
                Global.token+=auxToken

                SharedPreferencesHelper(this).setValueString(
                    "token",
                    auxToken
                )

                //println("------- LOCAL PREFERENCE TOKEN -----------")
                val aux:String=""
                val auxTest = this.getSharedPreferences("code", Context.MODE_PRIVATE)
                auxTest.getString("token","")
                //println(aux)

                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
            }
        }
    }
}