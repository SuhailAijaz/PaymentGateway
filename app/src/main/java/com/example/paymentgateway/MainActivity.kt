package com.example.paymentgateway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var imageView: ImageView
    private lateinit var success: TextView
    private lateinit var failed: TextView
    private lateinit var payButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView=findViewById(R.id.imageView)
        success=findViewById(R.id.success)
        failed=findViewById(R.id.failed)
        payButton=findViewById(R.id.pay_button)

        payButton.setOnClickListener(){
            makepayment()

        }
    }

    private fun makepayment() {
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Razorpay Corp")
            options.put("description","Demoing Charges")
            // You can omit the image option to fetch the image from the dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            options.put("amount","11199")//pass amount in currency subunits


            val prefill = JSONObject()
            prefill.put("email","")
            prefill.put("contact","")

            options.put("prefill",prefill)
            co.open(this,options)
        }catch (e: Exception){
//            Toast.makeText(this,"Error in payment: "+ e.message,Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
//        Toast.makeText(applicationContext,"successfully "+p0,Toast.LENGTH_SHORT).show()
        Log.e("TAG", "onPaymentSuccess: "+p0 )
        payButton.visibility= View.GONE
        success.visibility=View.VISIBLE
    }

    override fun onPaymentError(p0: Int, p1: String?) {
//        Toast.makeText(applicationContext,"failed "+p1,Toast.LENGTH_SHORT).show()
        payButton.visibility=View.GONE
        success.visibility=View.VISIBLE
    }
}