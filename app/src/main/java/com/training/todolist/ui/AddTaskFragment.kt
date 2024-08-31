package com.training.todolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.training.todolist.R
import com.training.todolist.data.Task
import com.training.todolist.model.TaskViewModel

class AddTaskFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var categorySpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)

        val titleEditText: EditText = view.findViewById(R.id.edit_text_title)
        categorySpinner = view.findViewById(R.id.spinner_category)
        val descriptionEditText: EditText = view.findViewById(R.id.edit_text_description)
        val saveButton: Button = view.findViewById(R.id.button_save)

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        // Set up the Spinner with the categories
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.task_categories, // The array of categories from strings.xml
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
        }

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val category = categorySpinner.selectedItem.toString()
            val description = descriptionEditText.text.toString()

            val task = Task(
                title = title,
                category = category,
                description = description
            )
            taskViewModel.insert(task)

            // Go back to com.training.todolist.ui.HomeFragment after saving the task
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}
