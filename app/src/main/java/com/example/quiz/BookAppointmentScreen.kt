package com.example.quiz

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class BookAppointmentScreen : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etPhone: EditText
    lateinit var etEmail: EditText
    lateinit var etCnic: EditText
    lateinit var spinnerType: Spinner
    lateinit var btnDOB: Button
    lateinit var btnDate: Button
    lateinit var btnTime: Button
    lateinit var radioGender: RadioGroup
    lateinit var checkTerms: CheckBox
    lateinit var btnConfirm: Button

    var dob = ""
    var appointmentDate = ""
    var appointmentTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        // Initialize views
        etName = findViewById(R.id.etName)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        etCnic = findViewById(R.id.etCnic)
        spinnerType = findViewById(R.id.spinnerType)
        btnDOB = findViewById(R.id.btnDOB)
        btnDate = findViewById(R.id.btnDate)
        btnTime = findViewById(R.id.btnTime)
        radioGender = findViewById(R.id.radioGender)
        checkTerms = findViewById(R.id.checkTerms)
        btnConfirm = findViewById(R.id.btnConfirm)

        // Spinner options
        val types = arrayOf(
            "Select Type",
            "Doctor Consultation",
            "Dentist Appointment",
            "Eye Specialist",
            "Skin Specialist",
            "General Checkup"
        )
        spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types)

        // --- Date of Birth Picker ---
        btnDOB.setOnClickListener {
            val cal = Calendar.getInstance()
            val dpd = DatePickerDialog(this,
                { _, year, month, day ->
                    dob = "$day/${month + 1}/$year"
                    btnDOB.text = dob
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show()
        }

        // --- Appointment Date Picker ---
        btnDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dpd = DatePickerDialog(this,
                { _, year, month, day ->
                    appointmentDate = "$day/${month + 1}/$year"
                    btnDate.text = appointmentDate
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show()
        }

        // --- Appointment Time Picker ---
        btnTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val tpd = TimePickerDialog(this,
                { _, hour, minute ->
                    appointmentTime = String.format(Locale.US, "%02d:%02d", hour, minute)
                    btnTime.text = appointmentTime
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            )
            tpd.show()
        }

        // --- Confirm Booking ---
        btnConfirm.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val cnic = etCnic.text.toString().trim()
            val type = spinnerType.selectedItem.toString()

            // --- Validations ---

            // Name
            if (name.isEmpty() || name.length < 3) {
                etName.error = "Name must be at least 3 characters"
                return@setOnClickListener
            }

            // Phone
            if (phone.isEmpty() || phone.length < 10) {
                etPhone.error = "Phone number must be at least 10 digits"
                return@setOnClickListener
            }

            // Email
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Enter valid Email"
                return@setOnClickListener
            }

            // CNIC (accept 13 digits with or without dashes)
            if (cnic.isEmpty() || !cnic.matches("\\d{13}|\\d{5}-\\d{7}-\\d{1}".toRegex())) {
                etCnic.error = "Enter valid CNIC (1234512345671 or 12345-1234567-1)"
                return@setOnClickListener
            }

            // DOB
            if (dob.isEmpty()) {
                Toast.makeText(this, "Select Date of Birth", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Appointment Type
            if (type == "Select Type") {
                Toast.makeText(this, "Select Appointment Type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Appointment Date
            if (appointmentDate.isEmpty()) {
                Toast.makeText(this, "Select Appointment Date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Appointment Time
            if (appointmentTime.isEmpty()) {
                Toast.makeText(this, "Select Appointment Time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Gender
            val genderId = radioGender.checkedRadioButtonId
            if (genderId == -1) {
                Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val gender = findViewById<RadioButton>(genderId).text.toString()

            // Terms & Conditions
            if (!checkTerms.isChecked) {
                Toast.makeText(this, "Accept Terms and Conditions", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // --- Pass data to ConfirmationScreen ---
            val intent = Intent(this, ConfirmationScreen::class.java)
            intent.putExtra("name", name)
            intent.putExtra("phone", phone)
            intent.putExtra("email", email)
            intent.putExtra("cnic", cnic)
            intent.putExtra("dob", dob)
            intent.putExtra("type", type)
            intent.putExtra("date", appointmentDate)
            intent.putExtra("time", appointmentTime)
            intent.putExtra("gender", gender)
            startActivity(intent)
        }
    }
}