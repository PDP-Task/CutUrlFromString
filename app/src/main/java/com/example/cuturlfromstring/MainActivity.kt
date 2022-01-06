package com.example.cuturlfromstring

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cuturlfromstring.adapter.Adapter
import com.example.cuturlfromstring.constants.Constants.SEND_ID
import com.example.cuturlfromstring.data.Message
import io.github.armcha.autolink.MODE_HASHTAG
import io.github.armcha.autolink.MODE_PHONE
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.message_item.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    var messageList = mutableListOf<Message>()
    private val array = ArrayList<String>()
    private lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycleView()
        clickEvents()
    }

    private fun clickEvents() {

        btn_send.setOnClickListener {
            val text = et_message.text.toString()
            if (text.isNotEmpty()) {
                linearLay.visibility = View.VISIBLE
            }
            addSearchView(text)
            sendMessage()
        }

        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }
    }

    private fun recycleView() {
        adapter = Adapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        var message = et_message.text.toString()
        val saveMessage = message
        val hashMessage = message

        if (isTelegram(message)) {
            val newString = message
            message = newString.replaceFirst("@", " t.me/")
        }
        if (isEmail(saveMessage)) {
            val text = saveMessage.replace("https://", " https://")
            message = text
        }
        if (isHashTag(hashMessage)) {
            val text = hashMessage.replace("#", " #")
            message = text
        }
        if (message.isNotEmpty()) {
            messageList.add(Message(message, SEND_ID))
            et_message.setText("")
            adapter.insertMessage(Message(message, SEND_ID))
            rv_messages.scrollToPosition(adapter.itemCount - 1)
        }

    }


    private fun addSearchView(message: String) {
        array.add(message)
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, array)
        autoText.setAdapter(arrayAdapter)

    }

    private fun isTelegram(text: String): Boolean {
        return text.startsWith("@") || text.contains("@")
    }

    private fun isHashTag(text: String): Boolean {
        return text.startsWith("#") || text.contains("#")
    }

    private fun isEmail(text: String): Boolean {
        return text.contains("https://")
    }
}
