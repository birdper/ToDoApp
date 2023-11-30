package com.birdper.todoapp.data.repository

import com.birdper.todoapp.presentation.model.TodoItem
import com.birdper.todoapp.data.datasource.HardcodedDataSource

class TodoItemRepository(
    private val dataSource: HardcodedDataSource = HardcodedDataSource(),
) {

    val todoItems = dataSource.data

    fun findTodoById(todoId: String): TodoItem? =
        dataSource.findTodoById(todoId)

    fun addTodo(newTodo: TodoItem) {
        dataSource.createTodo(newTodo)
    }

    fun updateTodo(todoId: String, updatedTodo: TodoItem) {
        dataSource.updateTodo(todoId, updatedTodo)
    }

    fun removeTodo(todoId: String) {
        dataSource.removeTodo(todoId)
    }
}