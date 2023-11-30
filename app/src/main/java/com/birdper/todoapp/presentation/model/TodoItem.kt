package com.birdper.todoapp.presentation.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class TodoItem(
    val id: String,
    val text: String,
    val priority: Priority,
    val isDone: Boolean = false,
    val creationDate: LocalDateTime,
    val modificationDate: LocalDateTime? = null,
    val deadlineDate: LocalDateTime? = null,
) {
    val deadlineDateText = deadlineDate?.format(
        DateTimeFormatter
            .ofPattern("dd MMMM yyyy", Locale.getDefault())
    ) ?: ""
}

enum class Priority {
    LOW, REGULAR, HIGH
}
