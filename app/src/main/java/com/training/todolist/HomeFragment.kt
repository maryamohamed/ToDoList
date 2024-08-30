package com.training.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: String?): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize ViewModel
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        // Set up RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        taskAdapter = TaskAdapter(taskViewModel)
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val category = arguments?.getString(ARG_CATEGORY)
        if (category == null) {
            // Show all tasks if no category is provided
            taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
                taskAdapter.submitList(tasks)
            }
        } else {
            // Filter tasks by the provided category
            taskViewModel.filterTasksByCategory(category).observe(viewLifecycleOwner) { tasks ->
                taskAdapter.submitList(tasks)
            }
        }

        // Set up FAB
        val fab: FloatingActionButton = view.findViewById(R.id.fab_add_task)
        fab.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddTaskFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}
