package com.example.aplikasichat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_data_chat.view.*

class PeopleAdapter(
    val context: Context,
    val dataPeople: ArrayList<People>
) : RecyclerView.Adapter<PeopleAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.activity_data_chat, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = dataPeople[position]

        holder.itemView.TV_dca_name.text = currentUser.fname

        holder.itemView.setOnClickListener {
            var intent = Intent(context, ChatMessageActivity::class.java)

            intent.putExtra("name", currentUser.fname)
            intent.putExtra("uid", currentUser.uid)

            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataPeople.size
    }


}