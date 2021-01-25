package edu.ufp.pam.pampaw_kotlin.HomePage


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.ufp.pam.pampaw_kotlin.CaptureImage.GalleryImageActivity
import edu.ufp.pam.pampaw_kotlin.Database.Invoice
import edu.ufp.pam.pampaw_kotlin.Database.InvoicesViewModel
import edu.ufp.pam.pampaw_kotlin.Profile.ProfileActivity
import edu.ufp.pam.pampaw_kotlin.R
import edu.ufp.pam.pampaw_kotlin.login.LoginActivity
import edu.ufp.pam.pampaw_kotlin.models.InvoiceInfo
import edu.ufp.pam.pampaw_kotlin.retrofit.RestApiService
import edu.ufp.pam.pampaw_kotlin.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_home_page.*
import java.lang.ref.WeakReference

class HomePageActivity : AppCompatActivity() {

    lateinit var newView: ImageView

    private lateinit var invoicesViewModel: InvoicesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        button_go_profile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        buttonGoAddInvoice.setOnClickListener{
            val intent = Intent(this, GalleryImageActivity::class.java)
            startActivity(intent)
        }

        button_logout.setOnClickListener{
            finish()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        invoicesViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
            ).get(InvoicesViewModel::class.java)

        invoicesViewModel.allInvoices.observe(
            this,
            Observer { invoices ->
                // Update cached list of invoices
                invoices?.let {
                    Log.e(
                        this.javaClass.simpleName,
                        "onChanged(): invoices.size=${invoices.size}"
                    )

                    linearLayout.removeAllViews()

                    var i = 0
                    for (invoice in it) {
                        decodeImageString(invoice)
                    }
                }
            })
    }

    fun createImageView(aux : Bitmap,invoice: Invoice){

        newView = ImageView(this)

        linearLayout.addView(newView)
        newView.setOnClickListener{
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra("Info",invoice.info)
            intent.putExtra("Name",invoice.invoiceName)
            intent.putExtra("ID",invoice.invoiceID)

            intent.putExtra("ImageString", invoice.image)
            startActivity(intent)
        }
        newView.setImageBitmap(aux)
    }

    fun decodeImageString(invoice: Invoice){

        val ind = invoice.image?.indexOf(",")
        val s2 = invoice.image?.substring(ind!! + 1)

        val imageBytes = Base64.decode(s2, Base64.DEFAULT)
        val decodeString= BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        createImageView(decodeString,invoice)
    }
}