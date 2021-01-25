package edu.ufp.pam.pampaw_kotlin.Profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.auth0.android.jwt.JWT
import edu.ufp.pam.pampaw_kotlin.HomePage.HomePageActivity
import edu.ufp.pam.pampaw_kotlin.R
import edu.ufp.pam.pampaw_kotlin.login.LoginActivity
import edu.ufp.pam.pampaw_kotlin.models.Profile
import edu.ufp.pam.pampaw_kotlin.retrofit.RestApiService
import edu.ufp.pam.pampaw_kotlin.store.Global
import edu.ufp.pam.pampaw_kotlin.store.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    lateinit var username: EditText
    lateinit var name: EditText
    lateinit var nif: EditText

    //var  prefs : SharedPreferences = this.getSharedPreferences("codes", Context.MODE_PRIVATE)

    val jwt = JWT(Global.token)

    override fun onCreate(savedInstanceState: Bundle?) {
       /* var aux: String= ""
        println("------- LOCAL PREFERENCE TOKEN -----------")
        println(prefs.getString("token",aux))
*/

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        username = findViewById(R.id.editTextUsernameProfile)
        username.setText(jwt.getClaim("username").asString())

        name = findViewById(R.id.editTextNameProfile)
        name.setText(jwt.getClaim("name").asString())

        nif = findViewById(R.id.editTextNifProfile)
        nif.setText(jwt.getClaim("NIF").asString())

        println("-----------NIF-------1---------")
        println(jwt.getClaim("NIF").asString())
        println("-----------NIF-------2--------")
        println(jwt.getClaim("NIF").asInt())

        /*nif = findViewById(R.id.editTextNifProfile)
        nif.setText(jwt.getClaim("NIF").asInt().toString())*/

        button_upload_profile.setOnClickListener(){
            updateProfile()
        }

        floatingProfileback.setOnClickListener{
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
    }

    fun updateProfile() {

        val apiService = RestApiService()

        val newPassCheck= (findViewById<EditText>(R.id.editTextNewPasswordCheckProfile)).text.toString()

        val values = Profile(
            id = jwt.getClaim("id").asInt(),
            username = (findViewById<EditText>(R.id.editTextUsernameProfile)).text.toString(),
            name = (findViewById<EditText>(R.id.editTextNameProfile)).text.toString(),
            password = (findViewById<EditText>(R.id.editTextPasswordProfile)).text.toString(),
            nif = (findViewById<EditText>(R.id.editTextNifProfile)).text.toString(),
            newPassword = (findViewById<EditText>(R.id.editTextNewPasswordProfile)).text.toString()
        )

        if(values.newPassword?.compareTo(newPassCheck)!=0){
            println("entrou nas palavras passe")
            return
        }

        apiService.updateProfile(values) {
            println(it)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}