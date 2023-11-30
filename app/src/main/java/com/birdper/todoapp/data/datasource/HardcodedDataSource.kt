package com.birdper.todoapp.data.datasource

import com.birdper.todoapp.presentation.model.Priority
import com.birdper.todoapp.presentation.model.TodoItem
import java.time.LocalDateTime
import kotlin.random.Random

class HardcodedDataSource {

    private var idIncrement = 1L

    private val _data = mutableListOf<TodoItem>()
    val data get() = _data.toList()

    init {
        populateData()
    }

    fun findTodoById(todoId: String): TodoItem? {
        return _data.find { it.id == todoId }
    }

    fun createTodo(newTodo: TodoItem) {
        _data.add(newTodo)
    }

    fun updateTodo(todoId: String, updatedTodo: TodoItem) {
        val index = _data.indexOfFirst { it.id == todoId }
        if (index == -1) return

        _data[index] = updatedTodo
    }

    fun removeTodo(todoId: String) {
        _data.removeIf { it.id == todoId }
    }

    private fun populateData() {
        repeat(30) {
            val isDone = Random.nextBoolean()
            val priority = Priority.entries[Random.nextInt(from = 0, until = 3)]

            val deadlineDate = if (Random.nextBoolean()) {
                LocalDateTime.now().plusDays(Random.nextLong(from = 2, until =  7))
            } else {
                null
            }

            val todoItem = TodoItem(
                id = idIncrement.toString(),
                text = "Content $idIncrement",
                priority = priority,
                isDone = isDone,
                creationDate = LocalDateTime.now(),
                deadlineDate = deadlineDate
            )
            idIncrement++

            _data.add(todoItem)
        }
    }
}
