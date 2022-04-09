package com.example.gra_losujaca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        val database = DatabaseHandler(applicationContext)
        val score = intent.getIntExtra("score", 0)
        val login = intent.getStringExtra("login").toString()
        val password = intent.getStringExtra("password").toString()

        val first_place = findViewById<TextView>(R.id.first_place)
        val second_place = findViewById<TextView>(R.id.second_place)
        val third_place = findViewById<TextView>(R.id.third_place)
        val fourth_place = findViewById<TextView>(R.id.fourth_place)
        val fifth_place = findViewById<TextView>(R.id.fifth_place)
        val sixth_place = findViewById<TextView>(R.id.sixth_place)
        val seventh_place = findViewById<TextView>(R.id.seventh_place)
        val eighth_place = findViewById<TextView>(R.id.eighth_place)
        val nineth_place = findViewById<TextView>(R.id.nineth_place)
        val tenth_place = findViewById<TextView>(R.id.tenth_place)
        val back_button = findViewById<Button>(R.id.back_button)

        first_place.text =
            database.getNick(0) + " wynik: " + database.getScore(database.getNick(0)).toString()
        second_place.text =
            database.getNick(1) + " wynik: " + database.getScore(database.getNick(1)).toString()
        third_place.text =
            database.getNick(2) + " wynik: " + database.getScore(database.getNick(2)).toString()
        fourth_place.text =
            database.getNick(3) + " wynik: " + database.getScore(database.getNick(3)).toString()
        fifth_place.text =
            database.getNick(4) + " wynik: " + database.getScore(database.getNick(4)).toString()
        sixth_place.text =
            database.getNick(5) + " wynik: " + database.getScore(database.getNick(5)).toString()
        seventh_place.text =
            database.getNick(6) + " wynik: " + database.getScore(database.getNick(6)).toString()
        eighth_place.text =
            database.getNick(7) + " wynik: " + database.getScore(database.getNick(7)).toString()
        nineth_place.text =
            database.getNick(8) + " wynik: " + database.getScore(database.getNick(8)).toString()
        tenth_place.text =
            database.getNick(9) + " wynik:  " + database.getScore(database.getNick(9)).toString()

        back_button.setOnClickListener(){
            if(intent.getStringExtra("Number").equals("Game")) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("login",login)
                intent.putExtra("password",password)
                intent.putExtra("score",score)

                startActivity(intent)

            } else {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)

            }
        }

    }

    override fun onStop() {
        super.onStop()
    }
}