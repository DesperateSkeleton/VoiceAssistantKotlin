package com.example.kotlin.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import java.util.*

class MessageListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var messageList: MutableList<Message> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view: View
        if (viewType == USER_TYPE) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_message, parent, false)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.assistant_message, parent, false)
        }
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(index: Int): Int {
        val message = messageList[index]
        return if (message.isSend) {
            USER_TYPE
        } else ASSISTANT_TYPE
    }

    companion object {
        private const val ASSISTANT_TYPE = 0
        private const val USER_TYPE = 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MessageViewHolder).bind(messageList[position])
    }
}