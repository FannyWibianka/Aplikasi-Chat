package com.example.aplikasichat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_chat_message.*

class ChatMessageActivity : AppCompatActivity() {


    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: FloatingActionButton
    private lateinit var messangerAdapter: MessangerAdapter
    private lateinit var messageList: ArrayList<Messanger>
    private lateinit var imageArrow : ImageView
    private lateinit var mDebRef: DatabaseReference


    var receiverRoom: String? = null
    var senderRoom: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_message)

//        val actionBar = actionBar
//        actionBar!!.title = "Fanny Wibianka"
//        actionBar!!.setDisplayHomeAsUpEnabled(true)


        // Untuk mendapatkan name dan user uid dari lawan bicara
        // yang dikirimkan dari user adapter

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        // untuk mendapatkan user Uid dari user yang menggunakan

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDebRef = FirebaseDatabase.getInstance().reference

        imageArrow = findViewById(R.id.iv_arrow)
        imageArrow.setOnClickListener {
            var intent = Intent(this, RecyclerDataChat::class.java)
            startActivity(intent)
        }
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        chatRecyclerView = findViewById(R.id.chatmassage)
        messageBox = findViewById(R.id.messagebox)
        sendButton = findViewById(R.id.floatingbutton)

        messageList = ArrayList()
        messangerAdapter = MessangerAdapter(this, messageList)

        chatRecyclerView.adapter = messangerAdapter
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.setHasFixedSize(true)

        // logic for adding data to recyclerview
        mDebRef.child("chats").child(senderRoom!!).child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        messageList.clear()

                        for (postSnapshot in snapshot.children) {
                            val message = postSnapshot.getValue(Messanger::class.java)
                            messageList.add(message!!)
                        }
                        messangerAdapter.notifyDataSetChanged()
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }

            })

        // adding the message to database
        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messaageObject = Messanger(message, senderUid)

            mDebRef.child("chats")
                .child(senderRoom!!)
                .child("message")
                .push()
                .setValue(messaageObject)
                .addOnSuccessListener {
                    mDebRef.child("chats")
                        .child(receiverRoom!!)
                        .child("message")
                        .push()
                        .setValue(messaageObject)
                        .addOnSuccessListener {
                        }
                }

            messageBox.text.clear()
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
}