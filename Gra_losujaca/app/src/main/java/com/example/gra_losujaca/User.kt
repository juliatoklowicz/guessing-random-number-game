package com.example.gra_losujaca

public class User {

    lateinit var login: String
    lateinit var password: String

    class User(login: String, password: String) {
        val login = login
        val password = password
    }
}