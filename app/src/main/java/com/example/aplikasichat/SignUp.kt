package com.example.aplikasichat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    lateinit var fullName       : EditText
    lateinit var emailAuth      : EditText
    lateinit var passwordAuth   : EditText
    lateinit var btnSignup      : Button
    lateinit var tvSignin       : TextView
    lateinit var fAuth          : FirebaseAuth
    lateinit var wDebRef        : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

    fullName        = findViewById(R.id.et_fullname)
    emailAuth       = findViewById(R.id.et_email)
    passwordAuth    = findViewById(R.id.et_password)
    btnSignup       = findViewById(R.id.btn_signup)
    tvSignin        = findViewById(R.id.TV_signin)
    fAuth           = FirebaseAuth.getInstance()
    wDebRef         = FirebaseDatabase.getInstance().reference




     tvSignin.setOnClickListener {
         var intent = Intent(this, signin::class.java)
         startActivity(intent)
     }
     btnSignup.setOnClickListener {


         val fname = fullName.text.toString()
         val email = emailAuth.text.toString()
         val password = passwordAuth.text.toString()

         if (fname != "" && email!= "" && password!= ""){
             signUP(email, password)
         }else{
             Toast.makeText(this, "Masih ada field yang kosong", Toast.LENGTH_LONG).show()
         }

     }

    }

    private fun signUP(email: String, password: String) {
            fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { here ->
                    if (here.isSuccessful){
                        auth()
                        Toast.makeText(this, "Sign Up berhasil", Toast.LENGTH_LONG).show()
                        fullName.text.clear()
                        emailAuth.text.clear()
                        passwordAuth.text.clear()
                        startActivity(Intent(this, signin::class.java))
                    }else{
                        Toast.makeText(this, "Sign Up gagal", Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun auth() {
        val name        = fullName.text.toString()
        val email       = emailAuth.text.toString()
        val uid         = fAuth.uid.toString()

        wDebRef = FirebaseDatabase.getInstance().reference
        wDebRef.child("user").child(uid).setValue(People(name, email, uid))
            Toast.makeText(this, "Data berhasil tersimpan", Toast.LENGTH_LONG).show()
    }


    override fun onStart() {
        super.onStart()
//        if (fAuth.currentUser != null){
//            Intent(this, signin::class.java).also {
//                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(it)
//            }
//        }
    }

}