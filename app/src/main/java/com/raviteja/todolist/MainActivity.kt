package com.raviteja.todolist

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextTask: EditText
    private lateinit var buttonAdd: Button
    private lateinit var linearTasks: LinearLayout

    private val tasks = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTask = findViewById(R.id.editTextTask)
        buttonAdd = findViewById(R.id.buttonAdd)
        linearTasks = findViewById(R.id.linearTasks)

        buttonAdd.setOnClickListener {
            val task = editTextTask.text.toString()
            if (task.isNotEmpty()) {
                tasks.add(task)
                updateTaskList()
                editTextTask.text.clear()
            }
        }

        updateTaskList()
    }

    private fun updateTaskList() {
        linearTasks.removeAllViews()

        for (task in tasks) {
            val taskView = createTaskView(task)
            linearTasks.addView(taskView)
        }
    }

    private fun createTaskView(task: String): View {
        val taskTextView = TextView(this)
        taskTextView.text = task
        taskTextView.textSize = 20F
        taskTextView.setTextColor(Color.BLACK)

        val taskTextViewLayoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        taskTextViewLayoutParams.setMargins(16, 8, 16, 8)
        taskTextView.layoutParams = taskTextViewLayoutParams

        val deleteButton = Button(this)
        deleteButton.text = "Delete"
        deleteButton.setBackgroundResource(R.drawable.button_background)
        deleteButton.setTextColor(Color.WHITE)
        deleteButton.textSize = 16F

        val deleteButtonLayoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        deleteButtonLayoutParams.setMargins(16, 8, 16, 8)
        deleteButton.layoutParams = deleteButtonLayoutParams

        deleteButton.setOnClickListener {
            tasks.remove(task)
            updateTaskList()
        }

        val updateButton = Button(this)
        updateButton.text = "Update"
        updateButton.setBackgroundResource(R.drawable.button_background)
        updateButton.setTextColor(Color.WHITE)
        updateButton.textSize = 16F

        val updateButtonLayoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        updateButtonLayoutParams.setMargins(16, 8, 16, 8)
        updateButton.layoutParams = updateButtonLayoutParams

        updateButton.setOnClickListener {
            showUpdateDialog(task)
        }

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.HORIZONTAL
        layout.addView(taskTextView)
        layout.addView(deleteButton)
        layout.addView(updateButton)

        return layout
    }

    private fun showUpdateDialog(oldTask: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update Task")

        val input = EditText(this)
        input.setText(oldTask)
        builder.setView(input)

        builder.setPositiveButton("Update") { _, _ ->
            val newTask = input.text.toString()
            if (newTask.isNotEmpty()) {
                val index = tasks.indexOf(oldTask)
                tasks[index] = newTask
                updateTaskList()
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}

