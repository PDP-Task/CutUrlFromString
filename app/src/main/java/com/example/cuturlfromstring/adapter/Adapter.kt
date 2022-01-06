package com.example.cuturlfromstring.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cuturlfromstring.R
import com.example.cuturlfromstring.constants.Constants.SEND_ID
import com.example.cuturlfromstring.constants.Time
import com.example.cuturlfromstring.data.Message
import io.github.armcha.autolink.MODE_HASHTAG
import io.github.armcha.autolink.MODE_PHONE
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.message_item.view.*

class Adapter : RecyclerView.Adapter<Adapter.MessageViewHolder>() {
    private var messageList = mutableListOf<Message>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            val time = Time.timeStamp()
            itemView.text_hour.text = time
            itemView.tv_message.apply {
                addAutoLinkMode(
                    MODE_HASHTAG,
                    MODE_PHONE
                )
                hashTagModeColor = Color.CYAN
                isClickable = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messageList[position]

        when (currentMessage.id) {
            SEND_ID -> {
                holder.itemView.tv_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
            }
        }
    }

    fun insertMessage(message: Message) {
        this.messageList.add(message)
        notifyItemInserted(messageList.size)
    }
}
