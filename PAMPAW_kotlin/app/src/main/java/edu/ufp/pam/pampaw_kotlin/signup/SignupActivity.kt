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
        println("NAME : "+ (findViewById<EditText>(R.id.editTextSignupName)).text)
        println("USERNAME : "+ (findViewById<EditText>(R.id.editTextSignupUsername)).text)
        println("PASSWORD : "+ (findViewById<EditText>(R.id.editTextSignupPassword)).text)
        println("NIF : "+ (findViewById<EditText>(R.id.editTextSignupNif)).text)

        val apiService = RestApiService()
        val userInfo = UserInfo(
            password = (findViewById<EditText>(R.id.editTextSignupName)).text,
            name = (findViewById<EditText>(R.id.editTextSignupName)).text,
            username = (findViewById<EditText>(R.id.editTextSignupName)).text,
            nif = (findViewById<EditText>(R.id.editTextSignupNif)).text
        )

        println("USER : $userInfo")

        apiService.addUser(userInfo) {
            println("NAME : "+ userInfo.name)
        }

        //println("Não entrou no outro : "+ userInfo.name)

    }
}