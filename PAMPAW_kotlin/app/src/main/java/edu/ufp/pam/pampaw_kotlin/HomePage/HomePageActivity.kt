package edu.ufp.pam.pampaw_kotlin.HomePage


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import edu.ufp.pam.pampaw_kotlin.R
import edu.ufp.pam.pampaw_kotlin.models.InvoiceInfo
import edu.ufp.pam.pampaw_kotlin.retrofit.RestApiService
import edu.ufp.pam.pampaw_kotlin.signup.SignupActivity
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

    fun createImageView(aux : Bitmap,invoiceInfo: InvoiceInfo){

        newView = ImageView(this)

        linearLayout.addView(newView)
        newView.setOnClickListener{
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra("Info",invoiceInfo.info)
            intent.putExtra("Name",invoiceInfo.name)
            intent.putExtra("ID",invoiceInfo.id)

            intent.putExtra("ImageString", invoiceInfo.image)
            startActivity(intent)
        }
        newView.setImageBitmap(aux)
    }

    fun getUserInvoices(){

        val apiService = RestApiService()

        apiService.getUserInvoices{ it ->
            println("it size: "+it?.data?.size)
            it?.data?.forEach{
                decodeImageString(it)
            }
        }
    }

    fun decodeImageString(invoiceInfo: InvoiceInfo){

        val imageBytes = Base64.decode(invoiceInfo.image?.removePrefix("data:image/jpeg;base64,"), Base64.DEFAULT)
        val decodeString= BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        createImageView(decodeString,invoiceInfo)
    }
}