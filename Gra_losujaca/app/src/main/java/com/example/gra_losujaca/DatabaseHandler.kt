package com.example.gra_losujaca

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class Message {
    var login : String? = null
    var password : String? = null
    var score : Int = 0

    constructor() {}

    constructor(login:String, password:String, score:Int) {
        this.login = login
        this.password = password
        this.score = score
    }
}

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "ScoreDatabase"
        private const val TABLE_CONTACTS = "ScoreTable"

        private const val KEY_ID = "_id"
        private const val KEY_LOGIN = "login"
        private const val KEY_PASSWORD = "password"
        private const val KEY_SCORE = "score"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_LOGIN + " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_SCORE + " INTEGER" + ")" )
        db?.execSQL(CREATE_CONTACTS_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }

    fun addToBase(message: Message): Int {
        val db = this.writableDatabase
        val password = message.password
        val login = message.login

        val squery = "SELECT * FROM $TABLE_CONTACTS WHERE $KEY_LOGIN =? "
        db.rawQuery(squery, arrayOf(login)).use {
            if(it.moveToFirst()) {
                return 0
            }
            else {
                val contentValues = ContentValues()
                contentValues.put(KEY_LOGIN, message.login)
                contentValues.put(KEY_PASSWORD, message.password)
                contentValues.put(KEY_SCORE, message.score)

                val success = db.insert(TABLE_CONTACTS, null, contentValues)

                db.close()
                return 1
            }
        }
    }

    fun login(message: Message): Int  {
        val db = this.writableDatabase
        val password = message.password
        val login = message.login

        val squery = "SELECT * FROM $TABLE_CONTACTS WHERE $KEY_LOGIN =? AND $KEY_PASSWORD =? "
        db.rawQuery(squery, arrayOf(login, password)).use {
            if(it.moveToFirst()) {
                return it.getInt(it.getColumnIndexOrThrow(KEY_SCORE))
            } else {
                return -1
            }
        }
        db.close()
    }

    fun getScore(nick: String): Int{
        val db = this.writableDatabase

        val search = "SELECT * FROM $TABLE_CONTACTS WHERE $KEY_LOGIN=?"
        db.rawQuery(search, arrayOf(nick)).use{
            if (it.moveToFirst()) {
                return it.getInt(it.getColumnIndexOrThrow(KEY_SCORE))
            } else{
                return 0
            }
        }
        db.close()
    }

    fun getNick(index: Int): String {
        val db = this.writableDatabase

        val search = "SELECT * FROM ${DatabaseHandler.TABLE_CONTACTS} ORDER BY ${DatabaseHandler.KEY_SCORE} DESC"
        db.rawQuery(search, arrayOf()).use {
            if (it.moveToPosition(index)) {
                return it.getString(it.getColumnIndexOrThrow(DatabaseHandler.KEY_LOGIN))
            } else{
                return " "
            }
        }
        db.close()
    }



    fun updateScoreInDb(message: Message){
        val db = this.writableDatabase
        val psswd = message.password
        val login = message.login
        val search = "SELECT * FROM $TABLE_CONTACTS WHERE $KEY_LOGIN=? AND $KEY_PASSWORD=? "
        db.rawQuery(search, arrayOf(login, psswd)).use {
            if (it.moveToFirst()) {
                if (it.getString(it.getColumnIndexOrThrow(KEY_PASSWORD)) == psswd) {
                    val values = ContentValues()
                    values.put(KEY_LOGIN, message.login)
                    values.put(KEY_PASSWORD, message.password)
                    values.put(KEY_SCORE, message.score)
                    val IDfromDB = it.getString(it.getColumnIndexOrThrow(KEY_ID))
                    db.update(TABLE_CONTACTS, values,"$KEY_ID=?" , arrayOf(IDfromDB))
                }
            }
            db.close()
        }
    }

}