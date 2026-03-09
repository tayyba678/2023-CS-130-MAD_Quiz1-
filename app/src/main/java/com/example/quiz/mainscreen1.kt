package com.example.quiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class mainscreen1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btnBook = findViewById<Button>(R.id.btnBookAppointment)

        btnBook.setOnClickListener {

            val intent = Intent(this, BookAppointmentScreen::class.java)
            startActivity(intent)

        }
    }
}