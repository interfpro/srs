package com.example.srs.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime

data class Card(val id: String,
                val deckId: String,
                val question: String,
                val answer: String,
                @JsonIgnore
                var dueDate: LocalDateTime,
                @JsonIgnore
                var interval: Long,
                @JsonIgnore
                var playerAnswer: String
)
