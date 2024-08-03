package com.example.srs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SrsApplication

fun main(args: Array<String>) {
	runApplication<SrsApplication>(*args)
}

@RestController
class MessageController {
	@GetMapping("/")
	fun card(@RequestParam("deckId") deckId: String,
			 @RequestParam("question") question: String,
			 @RequestParam("answer") answer: String) = "Answer, $answer!"
}
