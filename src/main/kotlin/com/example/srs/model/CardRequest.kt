package com.example.srs.model

data class CardRequest(val deckId: String,
                       val question: String,
                       val answer: String,
)