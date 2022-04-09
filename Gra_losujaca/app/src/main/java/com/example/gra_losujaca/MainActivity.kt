package com.example.gra_losujaca

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var buttonCheck: Button
    lateinit var buttonNewGame: Button
    lateinit var numberOfGuessings: TextView
    lateinit var buttonLogout: Button
    lateinit var scoreText: TextView
    lateinit var rankingButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var guessings = 0

        val builder = AlertDialog.Builder(this@MainActivity)

        editText = findViewById(R.id.EditText)
        buttonCheck = findViewById(R.id.buttonStrzelam)
        buttonNewGame = findViewById(R.id.buttonNewgame)
        numberOfGuessings = findViewById(R.id.liczbaStrzalow)
        buttonLogout = findViewById(R.id.buttonLogout)
        scoreText = findViewById(R.id.wynik)
        rankingButton = findViewById(R.id.rankingButton)

        val login = intent.getStringExtra("login").toString()
        val password = intent.getStringExtra("password").toString()
        val db = DatabaseHandler(applicationContext)

        var total_score = db.getScore(login)

        var random = getRandomNumber()
        var s = guessings.toString()
        numberOfGuessings.text = s

        scoreText.text = db.getScore(login).toString()

        val user = Message(login, password, total_score)

        rankingButton.setOnClickListener {
            val intent = Intent(this, RankingActivity::class.java)
            intent.putExtra("Number","Game")
            intent.putExtra("login", login)
            intent.putExtra("password", password)
            intent.putExtra("score", total_score)
            startActivity(intent)
        }

        buttonLogout.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        buttonNewGame.setOnClickListener {
            random = getRandomNumber()
            editText.text.clear()
            guessings = 0
            s = guessings.toString()
            numberOfGuessings.text = s

        }


        buttonCheck.setOnClickListener {
            val number: Int = editText.text.toString().toInt()

            if (number < 0 || number > 20) {
                Toast.makeText(applicationContext, "Liczba spoza przedziału! Spróbuj jeszcze raz!", Toast.LENGTH_SHORT).show()
            }
            else if (number < random) {
                guessings += 1
                s = guessings.toString()
                numberOfGuessings.text = s
                if (guessings == 10) {
                    builder.setTitle("Przegrana!")
                    builder.setMessage("Przekroczyłeś dozwoloną liczbę strzałów! Koniec gry!")

                    builder.setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->}

                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                    random = getRandomNumber()
                    editText.text.clear()
                    guessings = 0
                    s = guessings.toString()
                    numberOfGuessings.text = s
                }
                else {
                    Toast.makeText(applicationContext, "Liczba jest za mała!", Toast.LENGTH_SHORT).show()
                }
            } else if (number > random) {
                guessings += 1
                s = guessings.toString()
                numberOfGuessings.text = s
                if (guessings == 10) {
                    builder.setTitle("Przegrana!")
                    builder.setMessage("Przekroczyłeś dozwoloną liczbę strzałów! Koniec gry!")

                    builder.setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->}

                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                    random = getRandomNumber()
                    editText.text.clear()
                    guessings = 0
                    s = guessings.toString()
                    numberOfGuessings.text = s
                } else {
                    Toast.makeText(applicationContext, "Liczba jest za duża!", Toast.LENGTH_SHORT).show()
                }

            } else if (number == random) {
                guessings += 1
                s = guessings.toString()
                numberOfGuessings.text = s

                if (guessings == 1) {
                    total_score += 5
                    val a = Message(login, password, total_score)
                    db.updateScoreInDb(a)
                    scoreText.text = db.getScore(login).toString()
                }
                else if (guessings < 5) {
                    total_score += 3
                    val a = Message(login, password, total_score)
                    db.updateScoreInDb(a)
                    scoreText.text = db.getScore(login).toString()
                }
                else if (guessings < 7) {
                    total_score += 2
                    val a = Message(login, password, total_score)
                    db.updateScoreInDb(a)
                    scoreText.text = db.getScore(login).toString()
                }
                else {
                    total_score += 1
                    val a = Message(login, password, total_score)
                    db.updateScoreInDb(a)
                    scoreText.text = db.getScore(login).toString()
                }

                builder.setTitle("Zwycięstwo!")
                builder.setMessage("Gratulacje! Odgadłeś liczbę!")

                builder.setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->}

                val dialog: AlertDialog = builder.create()
                dialog.show()

                random = getRandomNumber()
                editText.text.clear()
                guessings = 0
                s = guessings.toString()
                numberOfGuessings.text = s
            }
        }

    }

    fun getRandomNumber(): Int {
        var random: Int = nextInt(0,20)
        return random
    }

    fun setRecord(newScore: Int){
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        val edit = sharedScore.edit()
        val score = getRecord()
        edit.putInt("score", score + newScore)
        edit.apply()
        val score2 = getRecord().toString()
        scoreText.text = score2
    }

    fun resetRecord() {
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        val edit = sharedScore.edit()
        edit.putInt("score", 0)
        edit.apply()
        val score = getRecord().toString()
        scoreText.text = score
    }

    fun getRecord(): Int{
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        var score = sharedScore.getInt("score", 0)
        return score
    }

    override fun onStop() {
        super.onStop()
    }

}


