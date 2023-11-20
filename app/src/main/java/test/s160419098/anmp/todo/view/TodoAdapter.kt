package test.s160419098.anmp.todo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.todo.R
import test.s160419098.anmp.todo.model.Todo

class TodoAdapter(
    private val todos: MutableList<Todo> = mutableListOf(),
    private val onItemCheck: (Todo) -> Unit,
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false),
            onItemCheck = { position -> onItemCheck(todos[position]) },
            onEditButtonClick = { position ->
                parent.findNavController().navigate(
                    TodoListFragmentDirections.editTodo(todos[position].id)
                )
            }
        )
    }

    override fun getItemCount() = todos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkboxItem.text = todos[position].title
    }

    fun setData(data: List<Todo>) {
        todos.clear()
        todos.addAll(data)
        notifyDataSetChanged()
    }

    fun removeItem(todo: Todo) {
        val index = todos.indexOf(todo)
        todos.removeAt(index)
        notifyItemRemoved(index)
    }

    class ViewHolder(
        view: View,
        onItemCheck: (position: Int) -> Unit,
        onEditButtonClick: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        val checkboxItem: CheckBox

        init {
            checkboxItem = view.findViewById(R.id.checkbox_todo_item)

            checkboxItem.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) onItemCheck(adapterPosition)
            }

            view.findViewById<Button>(R.id.button_edit_todo_item).setOnClickListener {
                onEditButtonClick(adapterPosition)
            }
        }
    }
}