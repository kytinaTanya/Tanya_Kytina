package com.example.myapplication.firebase

import com.example.myapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

lateinit var AUTH: FirebaseAuth
lateinit var UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: User

const val NODE_USERS = "users"
const val CHILD_ID = "id"
const val CHILD_NAME = "name"
const val CHILD_SURNAME = "surname"
const val CHILD_EMAIL = "email"
const val CHILD_PASSWORD = "password"

fun initFirebase(){
    AUTH = Firebase.auth
    REF_DATABASE_ROOT = Firebase.database.reference
    USER = User()
    UID = AUTH.currentUser?.uid.toString()
}