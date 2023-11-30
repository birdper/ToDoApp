package com.birdper.todoapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.birdper.todoapp.databinding.TodoItemBinding
import com.birdper.todoapp.presentation.model.TodoItem

class TodoItemAdapter(
    private val onEditTodo: (String) -> Unit = {},
    private val onDoneTodo: (TodoItem, Boolean) -> Unit = { _, _ ->  },
) : ListAdapter<TodoItem, TodoItemViewHolder>(TodoItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TodoItemBinding.inflate(inflater, parent, false)

        return TodoItemViewHolder(
            binding = binding,
            onEditTodo = onEditTodo,
            onDoneTodo = onDoneTodo
        )
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: TodoItemViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isEmpty() || payloads.first() !is Boolean) {
            return super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.onBindDoneState(getItem(position).isDone)
        }
    }
}


object TodoItemDiffCallback : DiffUtil.ItemCallback<TodoItem>() {

    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: TodoItem, newItem: TodoItem): Any? {
        if (oldItem.isDone != newItem.isDone) return newItem.isDone

        return super.getChangePayload(oldItem, newItem)
    }
}
