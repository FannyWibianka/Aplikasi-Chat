package com.example.aplikasichat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class signin : AppCompatActivity() {

    lateinit var emailSi : EditText
    lateinit var passwordSi : EditText
    lateinit var btn_signin : Button
    lateinit var TV_signup : TextView
    lateinit var fAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        emailSi = findViewById(R.id.et_emailsignin)
        passwordSi = findViewById(R.id.et_passwordsi)
        btn_signin = findViewById(R.id.btn_signin)
        TV_signup = findViewById(R.id.TV_signup)
        fAuth = FirebaseAuth.getInstance()


        TV_signup.setOnClickListener {
            var intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btn_signin.setOnClickListener {
            val email = emailSi.text.toString()
            val password = passwordSi.text.toString()

            if (email != "" && password!= ""){
                signIn(email, password)
            }else{
                Toast.makeText(this, "Masih ada field yang kosong", Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun signIn(email: String, password: String) {
        fAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, RecyclerDataChat::class.java)
                    startActivity(intent)
                    emailSi.text.clear()
                    passwordSi.text.clear()
//                        startActivity(Intent(this, RvUserActivity::class.java))
                    // Sign in success, update UI with the signed-in user's information
                } else {
                    Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT).show()

                }
            }
    }

    override fun onStart() {
        super.onStart()
//        if (fAuth.currentUser != null){
//            Intent(this, RecyclerDataChat::class.java).also {
//                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(it)
//            }
//        }
    }
}