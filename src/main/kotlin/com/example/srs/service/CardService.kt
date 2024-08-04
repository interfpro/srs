package com.example.srs.service

import com.example.srs.model.Card
import com.example.srs.model.CardAnswerRequest
import com.example.srs.model.CardRequest
import com.example.srs.model.CardResult
import com.example.srs.repository.CardRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CardService(private val cardRepository: CardRepository) {

    fun addCard(cardRequest: CardRequest) {
        cardRepository.addCard(cardRequest)
    }

    fun getDueCards(deckId: String): List<Card> {
        return cardRepository.getDueCards(deckId)
    }

    fun updateCardWithPlayerAnswer(cardAnswerRequest: CardAnswerRequest) {
        val card = cardRepository.getCardById(cardAnswerRequest.id)
        if (card != null) {
            card.playerAnswer = cardAnswerRequest.answer
            cardRepository.updateCard(card)
        }
    }

    fun getResults(): List<CardResult> {
        val cards = cardRepository.getCards()
        val results = mutableListOf<CardResult>()
        for (card in cards) {
            results.add(getCardResult(card))
        }
        return results
    }

    private fun getCardResult(card: Card): CardResult {
        val cardResult: CardResult
        if (card.answer.equals(card.playerAnswer)) {
            card.interval *= 2
            cardResult = CardResult(card.id, "Correct")
        } else {
            card.interval = 5
            cardResult = CardResult(card.id, "Incorrect")
        }
        card.dueDate = LocalDateTime.now()
        card.playerAnswer = ""
        cardRepository.updateCard(card)

        return cardResult
    }

}
