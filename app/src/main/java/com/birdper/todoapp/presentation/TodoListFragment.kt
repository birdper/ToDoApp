package com.birdper.todoapp.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.birdper.todoapp.R
import com.birdper.todoapp.data.repository.TodoItemRepository
import com.birdper.todoapp.databinding.FragmentTodoListBinding
import com.birdper.todoapp.presentation.adapter.TodoItemAdapter
import com.birdper.todoapp.presentation.model.TodoItem

class TodoListFragment : Fragment(R.layout.fragment_todo_list) {

    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TodoItemAdapter
    private val repository = TodoItemRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentTodoListBinding.bind(view)

        setupView()
        setupInsets()
        setupRecyclerView()

        adapter.submitList(repository.todoItems)
    }

    private fun setupView() {
        val countDoneTodos = repository.todoItems.count { it.isDone }

        binding.tvCountDoneTodos.text =
            getString(R.string.todolist_subtitle_msg_done_count, countDoneTodos)
    }

    private fun setupRecyclerView() {
        adapter = TodoItemAdapter(
            onEditTodo = ::onEditTodo,
            onDoneTodo = ::onDoneTodo,
        )
        binding.rvTodoItems.adapter = adapter
    }

    private fun setupInsets() {
        with(binding) {
            rvTodoItems.doOnApplyWindowInsets { view, insets ->
                view.updatePadding(
                    bottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
                )
                insets
            }

            fabAddTodoItem.doOnApplyWindowInsets { view, inInsets ->
                val insetsNavBar = inInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
                view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin += insetsNavBar.bottom
                }
                inInsets
            }
        }
    }

    private fun onEditTodo(todoId: String) {

    }

    private fun onDoneTodo(todoItem: TodoItem, isDone: Boolean) {

    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
