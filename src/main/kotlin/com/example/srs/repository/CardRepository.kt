package com.example.srs.repository

import com.example.srs.model.Card
import com.example.srs.model.CardRequest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Repository
class CardRepository(private val jdbcTemplate: JdbcTemplate) {

    fun addCard(card: CardRequest) {
        val id = UUID.randomUUID().toString()
        val dueDate = LocalDateTime.now().toString()
        jdbcTemplate.update(
            "INSERT INTO cards (id, deck_id, question, answer, due_date, interv, player_answer) VALUES (?, ?, ?, ?, ?, ?, ?)",
            id, card.deckId, card.question, card.answer, dueDate, 5, ""
        )
    }

    fun getDueCards(deckId: String): List<Card> {
        val sql = """
            SELECT id, deck_id, question, answer, due_date, interv, player_answer
            FROM cards
            WHERE deck_id = ? 
              AND DATEADD('MINUTE', interv, due_date) < CURRENT_TIMESTAMP
              AND player_answer = ''
        """
        return jdbcTemplate.query(sql, arrayOf(deckId)) { rs, _ ->
            mapRowToCardResponse(rs)
        }
    }

    fun getCards(): List<Card> {
        val sql = """
            SELECT * FROM cards
        """
        return jdbcTemplate.query(sql, arrayOf()) { rs, _ ->
            mapRowToCardResponse(rs)
        }
    }

    fun updateCard(card: Card) {
        val sql = """
        MERGE INTO cards (id, deck_id, question, answer, due_date, interv, player_answer)
        KEY (id)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """
        jdbcTemplate.update(sql, card.id, card.deckId, card.question, card.answer, card.dueDate.toString(), card.interval, card.playerAnswer)
    }

    fun getCardById(id: String): Card? {
        val sql = "SELECT * FROM cards WHERE id = ?"
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return try {
            jdbcTemplate.queryForObject(sql, arrayOf(id)) { rs, _ ->
                Card(
                    id = rs.getString("id"),
                    deckId = rs.getString("deck_id"),
                    question = rs.getString("question"),
                    answer = rs.getString("answer"),
                    dueDate = LocalDateTime.parse(rs.getString("due_date"), formatter),
                    interval = rs.getLong("interv"),
                    playerAnswer = rs.getString("player_answer")
                )
            }
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    private fun mapRowToCardResponse(rs: ResultSet): Card {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return Card(
            id = rs.getString("id"),
            deckId = rs.getString("deck_id"),
            question = rs.getString("question"),
            answer = rs.getString("answer"),
            dueDate = LocalDateTime.parse(rs.getString("due_date"), formatter),
            interval = rs.getLong("interv"),
            playerAnswer = rs.getString("player_answer"),
        )
    }

}
