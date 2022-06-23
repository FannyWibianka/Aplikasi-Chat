package com.example.aplikasichat

import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RecyclerDataChat : AppCompatActivity() {

    private lateinit var peopleRecyclerView: RecyclerView
    private lateinit var dataPeople: ArrayList<People>
    private lateinit var userAdapter: PeopleAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDebRef : DatabaseReference
    private lateinit var imageView: ImageView
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_data_chat)

        imageView = findViewById(R.id.iv_logout)
        imageView.setOnClickListener {
                mAuth.signOut()
                Intent(this, signin::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
        }
        mAuth = FirebaseAuth.getInstance()
        mDebRef = FirebaseDatabase.getInstance().reference

        dataPeople = ArrayList()

        userAdapter = PeopleAdapter(this, dataPeople)

        peopleRecyclerView = findViewById(R.id.RV_chat)

        peopleRecyclerView.adapter = userAdapter
        peopleRecyclerView.layoutManager = LinearLayoutManager(this)
        peopleRecyclerView.setHasFixedSize(true)

        mDebRef.child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataPeople.clear()
                for (postSnapshot in snapshot.children){
                    val chat = postSnapshot.getValue(People::class.java)
                    dataPeople.add(chat!!)

                    }
                    userAdapter.notifyDataSetChanged()
                }


            override fun onCancelled(error: DatabaseError) {
            }

        })

    }




}
