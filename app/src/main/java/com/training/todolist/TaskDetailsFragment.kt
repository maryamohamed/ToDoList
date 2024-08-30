package com.training.todolist

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

class TaskDetailsFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var categorySpinner: Spinner
    private var taskId: Int? = null
    private var task: Task? = null

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

        // Retrieve the task ID passed as an argument
        taskId = arguments?.getInt("task_id")

        taskId?.let { id ->
            // Fetch the task from the ViewModel using the task ID
            taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
                task = tasks.find { it.id == id }
                task?.let {
                    titleEditText.setText(it.title)
                    categorySpinner.setSelection(
                        (categorySpinner.adapter as ArrayAdapter<String>).getPosition(it.category)
                    )
                    descriptionEditText.setText(it.description)
                }
            }
        }

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val category = categorySpinner.selectedItem.toString()
            val description = descriptionEditText.text.toString()

            if (task != null) {
                // Update the existing task
                task?.apply {
                    this.title = title
                    this.category = category
                    this.description = description
                }
                task?.let { taskViewModel.updateTask(it) }
            } else {
                // Insert a new task
                val newTask = com.training.todolist.Task(
                    title = title,
                    category = category,
                    description = description
                )
                taskViewModel.insert(newTask)
            }

            // Go back to HomeFragment after saving the task
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}
