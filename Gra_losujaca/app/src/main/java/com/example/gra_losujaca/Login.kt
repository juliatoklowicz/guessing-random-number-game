package com.example.gra_losujaca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    lateinit var enterLogin: EditText
    lateinit var enterPassword: EditText
    lateinit var buttonLogin: Button
    lateinit var buttonRegister: Button
    lateinit var buttonRanking: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        val database = DatabaseHandler(applicationContext)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        enterLogin = findViewById(R.id.enterLogin)
        enterPassword = findViewById(R.id.enterPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)
        buttonRanking = findViewById(R.id.buttonRanking)


        buttonRanking.setOnClickListener {
            val intent = Intent(this, RankingActivity::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            if(enterLogin.text.trim().isNotEmpty() && enterPassword.text.trim().isNotEmpty()) {
                var score = 0
                val user = Message(enterLogin.text.trim().toString(), enterPassword.text.trim().toString(), score)
                val dbReturn = database.login(user)
                if(dbReturn != -1) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("login", enterLogin.text.trim().toString())
                    intent.putExtra("password", enterPassword.text.trim().toString())
                    intent.putExtra("score", score)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Niepoprawne dane!", Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(applicationContext, "Podaj wszystkie dane!", Toast.LENGTH_LONG).show()
            }
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            intent.putExtra("Number", "Loggger")
            startActivity(intent)
        }

    }

    override fun onStop() {
        super.onStop()
    }

}
