package com.example.quiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmationScreen : AppCompatActivity() {

    lateinit var tvDetails: TextView
    lateinit var btnBackMain: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        tvDetails = findViewById(R.id.tvDetails)
        btnBackMain = findViewById(R.id.btnBackMain)

        // Get data from intent
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")
        val cnic = intent.getStringExtra("cnic")
        val dob = intent.getStringExtra("dob")
        val type = intent.getStringExtra("type")
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")
        val gender = intent.getStringExtra("gender")

        // Show details
        @Suppress("SetTextI18n")
        tvDetails.text = """
        Name: $name
        Phone: $phone
        Email: $email
        CNIC: $cnic
        Date of Birth: $dob
        Appointment Type: $type
        Appointment Date: $date
        Appointment Time: $time
        Gender: $gender
        """.trimIndent()
        // Back to Main Screen
        btnBackMain.setOnClickListener {
            val intent = Intent(this, mainscreen1::class.java)
            // Clear previous screens from stack
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}