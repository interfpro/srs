package com.example.srs.app

import com.example.srs.model.CardAnswerRequest
import com.example.srs.model.CardRequest
import com.example.srs.service.CardService
import org.springframework.web.bind.annotation.*

@RestController
class CardController(val cardService: CardService) {

    @PostMapping("/takes")
    fun addCard(@RequestBody cardRequest: CardRequest) = cardService.addCard(cardRequest)

    @GetMapping("/takes")
    fun getDueCards(@RequestParam(name = "deckId") deckId: String) = cardService.getDueCards(deckId)

    @PutMapping("/answer-to-card")
    fun answerToCard(@RequestBody cardAnswerRequest: CardAnswerRequest) = cardService.updateCardWithPlayerAnswer(cardAnswerRequest)

    @GetMapping("/results")
    fun getDResults() = cardService.getResults()
}