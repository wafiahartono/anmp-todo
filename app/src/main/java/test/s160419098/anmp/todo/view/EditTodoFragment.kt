package test.s160419098.anmp.todo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import test.s160419098.anmp.todo.databinding.FragmentEditTodoBinding
import test.s160419098.anmp.todo.model.Todo
import test.s160419098.anmp.todo.viewmodel.TodoDetailViewModel

class EditTodoFragment : Fragment() {
    private var _binding: FragmentEditTodoBinding? = null
    private val binding get() = _binding!!

    private val todoViewModel: TodoDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        todoViewModel.fetch(
            EditTodoFragmentArgs.fromBundle(requireArguments()).id
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.onSaveClickListener = OnClickListener { saveChanges() }

        todoViewModel.todo.observe(viewLifecycleOwner) {
            binding.todo = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveChanges() {
        todoViewModel.update(
            Todo(
                title = binding.editTextTodoTitle.text.toString(),
                notes = binding.editTextTodoNotes.text.toString(),
                priority = binding.radioGroupPriority.findViewById<RadioButton>(
                    binding.radioGroupPriority.checkedRadioButtonId
                ).tag.toString().toInt(),
            ).apply {
                id = EditTodoFragmentArgs.fromBundle(requireArguments()).id
            }
        )
        Toast.makeText(requireContext(), "Changes saved", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(binding.root).popBackStack()
    }
}