package com.training.todolist.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.training.todolist.R
import com.training.todolist.data.Task

class TaskAdapter(
    private val taskViewModel: TaskViewModel,
    private val onTaskClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TasksComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = getItem(position)
        holder.bind(currentTask)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskTitle: TextView = itemView.findViewById(R.id.text_view_title)
        private val taskCategory: TextView = itemView.findViewById(R.id.text_view_category)
        private val taskDesc: TextView = itemView.findViewById(R.id.text_view_description)
        private val deleteIcon: ImageView = itemView.findViewById(R.id.delete)

        fun bind(task: Task) {
            taskTitle.text = task.title
            taskCategory.text = task.category
            taskDesc.text = task.description

            // Handle item click
            itemView.setOnClickListener {
                onTaskClick(task)
            }

            deleteIcon.setOnClickListener {
                taskViewModel.deleteTask(task)
            }
        }
    }

    class TasksComparator : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}
