package edu.ufp.pam.pampaw_kotlin.HomePage

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import edu.ufp.pam.pampaw_kotlin.R
import edu.ufp.pam.pampaw_kotlin.retrofit.RestApiService
import kotlinx.android.synthetic.main.activity_capture.*
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        floatingPictureback.setOnClickListener{
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }

        button_delete_image.setOnClickListener{
            deleteImage()
        }

        val aux: String = intent.extras?.getCharSequence("ImageString") as String

        val ind = aux.indexOf(",")
        val s2 = aux.substring(ind + 1)

        val imageBytes = Base64.decode(s2, Base64.DEFAULT)

        val decodeString= BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        textView_set_OCR_info.text = intent.extras?.getCharSequence("Info")
        textView_name_invoice.text = intent.extras?.getCharSequence("Name")
        imageView_imageActivity.setImageBitmap(decodeString)
    }

    fun deleteImage(){

        val apiService = RestApiService()
        val response = intent.extras?.getInt("ID")
        apiService.deleteInvoice(response!!) {
            println("Invoice added")
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
    }
}
