package com.example.kotlin

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.messages.Message
import com.example.kotlin.messages.MessageListAdapter
import java.text.ParseException
import java.util.*
import java.util.function.Consumer

open class MainActivity : AppCompatActivity()
{
    protected var sendButton: Button? = null
    protected var questionText: EditText? = null
    protected var textToSpeech: TextToSpeech? = null
    protected var chatMessageList: RecyclerView? = null
    protected var messageListAdapter: MessageListAdapter? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendButton = findViewById(R.id.sendButton)
        questionText = findViewById(R.id.questionField)
        chatMessageList = findViewById(R.id.chatMessageList)
        messageListAdapter = MessageListAdapter()
        chatMessageList?.setLayoutManager(LinearLayoutManager(this))
        chatMessageList?.setAdapter(messageListAdapter)
        sendButton?.setOnClickListener(View.OnClickListener { onSend() })
        textToSpeech = TextToSpeech(applicationContext, OnInitListener
        { status ->
            if (status != TextToSpeech.ERROR)
            {
                textToSpeech!!.language = Locale("ru")
            }
        })
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle)
    {
        super.onRestoreInstanceState(savedInstanceState)
        questionText!!.setText(savedInstanceState.getString("question"))
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        outState.putString("question", questionText!!.text.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onSend()
    {
        try
        {
            val text = questionText!!.text.toString()
            messageListAdapter?.messageList?.add(Message(text, true))
            messageListAdapter?.notifyDataSetChanged()
            val answer = arrayOfNulls<String>(1)
            messageListAdapter?.messageList?.size?.minus(1)?.let {
                chatMessageList!!.scrollToPosition(
                    it
                )
            }
            AI.getAnswer(text, this@MainActivity, object : Consumer<String?>
            {
                override fun accept(t: String?)
                {
                    answer[0] = t
                    answer[0]?.let { Message(it, false) }?.let {
                        messageListAdapter?.messageList?.add(
                            it
                        )
                    }
                    messageListAdapter?.notifyDataSetChanged()
                    chatMessageList!!.scrollToPosition((messageListAdapter?.messageList?.size ?: - 1))
                    questionText!!.setText("")
                    textToSpeech!!.speak(answer[0], TextToSpeech.QUEUE_FLUSH, null, null)
                }
            })
        } catch (e: ParseException)
        {
        }
    }
}