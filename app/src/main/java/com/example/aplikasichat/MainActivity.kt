package com.example.aplikasichat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_utama)

        var handler = Handler()
        handler.postDelayed({
            var intent = Intent(this@MainActivity, signin::class.java)
            startActivity(intent)
         finish()

        }, 5000)
    }
}

