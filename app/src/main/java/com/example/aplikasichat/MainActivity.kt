package com.example.aplikasichat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var fAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_utama)
        fAuth = FirebaseAuth.getInstance()

        if(fAuth.currentUser != null) {
            var handler = Handler()
            handler.postDelayed({
                var intent = Intent(this@MainActivity, RecyclerDataChat::class.java)
                startActivity(intent)
                finish()

            }, 5000)
        }else{
            var handler = Handler()
            handler.postDelayed({
                var intent = Intent(this@MainActivity, signin::class.java)
                startActivity(intent)
             finish()

            }, 5000)
        }
    }
}

