package com.birdper.todoapp.presentation.adapter

import android.graphics.Paint
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.birdper.todoapp.databinding.TodoItemBinding
import com.birdper.todoapp.presentation.model.Priority
import com.birdper.todoapp.presentation.model.TodoItem
import com.google.android.material.color.MaterialColors
import com.birdper.todoapp.R as appR
import com.google.android.material.R as materialR

class TodoItemViewHolder(
    private val binding: TodoItemBinding,
    private val onEditTodo: (String) -> Unit,
    private val onDoneTodo: (TodoItem, Boolean) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var todoItem: TodoItem

    init {
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        with(binding) {
            root.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                onEditTodo(todoItem.id)
            }

            chkDone.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                onDoneTodo(todoItem, binding.chkDone.isChecked)
            }
        }
    }

    fun onBind(item: TodoItem) {
        todoItem = item

        with(binding) {
            chkDone.isChecked = item.isDone
            chkDone.setColorByPriority(item.priority)

            setPriorityIcon(item.priority)

            tvContentTodo.text = item.text
            tvContentTodo.shadeTextColor(item.isDone)
            tvContentTodo.strikeThrough(item.isDone)

            tvDeadlineDate.isGone = item.deadlineDateText.isBlank()
            tvDeadlineDate.text = item.deadlineDateText
            tvDeadlineDate.shadeTextColor(item.isDone)
        }
    }

    fun onBindDoneState(isDone: Boolean) {
        with(binding) {
            tvContentTodo.strikeThrough(isDone)
            tvContentTodo.shadeTextColor(isDone)
            tvDeadlineDate.shadeTextColor(isDone)
        }
    }

    private fun CheckBox.setColorByPriority(priority: Priority) {
        val color = if (priority == Priority.HIGH) {
            appR.color.selector_checkbox_high_priority
        } else {
            appR.color.selector_checkbox_regular_priority
        }

        buttonTintList = ContextCompat.getColorStateList(
            binding.root.context,
            color
        )
    }

    private fun TextView.shadeTextColor(isDone: Boolean) {
        val color = if (isDone) {
            MaterialColors.getColor(this, materialR.attr.colorOnTertiary)
        } else {
            MaterialColors.getColor(this, materialR.attr.colorOnPrimary)
        }
        setTextColor(color)
    }

    private fun TextView.strikeThrough(isShouldStrike: Boolean) {
        paintFlags = if (isShouldStrike) {
            paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    private fun setPriorityIcon(priority: Priority) {
        with(binding) {
            icPriority.isGone = false

            when (priority) {
                Priority.LOW -> {
                    icPriority.setImageResource(appR.drawable.ic_arrow_downward)
                }

                Priority.REGULAR -> {
                    icPriority.isGone = true
                }

                Priority.HIGH -> {
                    icPriority.setImageResource(appR.drawable.ic_high_priority)
                }
            }
        }
    }
}
