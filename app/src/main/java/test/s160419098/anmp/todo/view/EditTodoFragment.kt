package test.s160419098.anmp.todo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import test.s160419098.anmp.todo.R
import test.s160419098.anmp.todo.model.Todo
import test.s160419098.anmp.todo.viewmodel.TodoDetailViewModel

class EditTodoFragment : Fragment() {
    val todoViewModel: TodoDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        todoViewModel.fetch(
            EditTodoFragmentArgs.fromBundle(requireArguments()).id
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textHeading: TextView = view.findViewById(R.id.text_heading)
        val editTextTitle: EditText = view.findViewById(R.id.edit_text_todo_title)
        val editTextNotes: EditText = view.findViewById(R.id.edit_text_todo_notes)
        val radioGroupPriority: RadioGroup = view.findViewById(R.id.radio_group_priority)
        val buttonAdd: Button = view.findViewById(R.id.button_add_todo)

        textHeading.text = "Edit To-Do"
        buttonAdd.text = "Save changes"

        buttonAdd.setOnClickListener {
            todoViewModel.update(
                Todo(
                    title = editTextTitle.text.toString(),
                    notes = editTextNotes.text.toString(),
                    priority = radioGroupPriority.findViewById<RadioButton>(
                        radioGroupPriority.checkedRadioButtonId
                    ).tag.toString().toInt(),
                ).apply {
                    id = EditTodoFragmentArgs.fromBundle(requireArguments()).id
                }
            )
            Toast.makeText(requireContext(), "Changes saved", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }

        todoViewModel.todo.observe(viewLifecycleOwner) {
            editTextTitle.setText(it.title)
            editTextNotes.setText(it.notes)
            radioGroupPriority.findViewWithTag<RadioButton>(it.priority.toString())?.isChecked =
                true
        }
    }
}