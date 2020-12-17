package edu.ufp.pam.pampaw_kotlin.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import edu.ufp.pam.pampaw_kotlin.R
import edu.ufp.pam.pampaw_kotlin.models.UserInfo
import edu.ufp.pam.pampaw_kotlin.retrofit.RestApiService
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        button_Signup.setOnClickListener{
            addDummyUser()
        }
    }

    fun addDummyUser() {

        val apiService = RestApiService()
        val userInfo = UserInfo(

            username = (findViewById<EditText>(R.id.editTextSignupUsername)).text.toString(),
            name = (findViewById<EditText>(R.id.editTextSignupName)).text.toString(),
            password = (findViewById<EditText>(R.id.editTextSignupPassword)).text.toString(),
            nif = (findViewById<EditText>(R.id.editTextSignupNif)).text.toString().toInt()
        )

        println("USER : $userInfo")

        apiService.addUser(userInfo) {
            println("should be added")
        }

        //println("Não entrou no outro : "+ userInfo.name)

    }
}