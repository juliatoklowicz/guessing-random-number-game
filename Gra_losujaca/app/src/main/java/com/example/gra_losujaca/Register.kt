package com.example.gra_losujaca

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    lateinit var passwordEntry: EditText
    lateinit var passwordEntry2: EditText
    lateinit var registerButton: Button
    lateinit var usernameEntry: EditText


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        passwordEntry = findViewById(R.id.passwordEntry)
        passwordEntry2 = findViewById(R.id.passwordEntry2)
        registerButton = findViewById(R.id.registerButton)
        usernameEntry = findViewById(R.id.usernameEntry)
        val database = DatabaseHandler(applicationContext)

        registerButton.setOnClickListener {
            var score = 0
            val user = Message(usernameEntry.text.trim().toString(), passwordEntry.text.trim().toString(), score)
            if(usernameEntry.text.trim().isNotEmpty() && passwordEntry.text.trim().isNotEmpty() && passwordEntry2.text.trim().isNotEmpty()) {
                if (passwordEntry.text.trim().toString() == passwordEntry2.text.trim().toString()) {
                    if (database.addToBase(user) == 1) {
                        database.updateScoreInDb(user)
                        Toast.makeText(applicationContext, "Pomyślnie dodano użytkownika do bazy danych!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Użytkownik z podanym loginem już istnieje!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Hasła nie są takie same!", Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(applicationContext, "Podaj wszystkie dane!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
    }



}


