package com.example.kotlin.messages

import java.util.*

class Message(var text: String, var isSend: Boolean) {
    var date: Date

    init {
        date = Date()
    }
}