package edu.ufp.pam.pampaw_kotlin.HomePage


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import edu.ufp.pam.pampaw_kotlin.R
import edu.ufp.pam.pampaw_kotlin.retrofit.RestApiService
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePageActivity : AppCompatActivity() {

    lateinit var newView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        button_get_invoices.setOnClickListener{
            getUserInvoices()
        }

    }

    fun createImageView(aux : Bitmap){
        newView = ImageView(this)

        myLayout.addView(newView)

        newView.setImageBitmap(aux)
        newView.layoutParams.height = 600
        newView.layoutParams.width = 600

    }

    fun getUserInvoices(){

        val apiService = RestApiService()

        apiService.getUserInvoices{ it ->
            println("it size: "+it?.data?.size)
            it?.data?.forEach{
                it.image?.let { it1 -> decodeImageString(it1) }
            }

        }
    }

    fun decodeImageString(aux : String){

        val imageBytes = Base64.decode(aux.removePrefix("data:image/jpeg;base64,"), Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        createImageView(decodedImage)
    }
}