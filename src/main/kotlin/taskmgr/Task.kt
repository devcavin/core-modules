package io.github.devcavin.taskmgr

import java.util.UUID


data class Task(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val priority: Int,
    var isCompleted: Boolean = false
)

class TaskService {
    private val tasks = mutableListOf<Task>()
    // add a new task
    fun addTask(task: Task): Task {
        tasks.add(task)
        return task
    }

    // fetching all tasks
    fun allTasks(): List<Task> {
        return tasks
    }

    // find task by id
    fun byId(id: UUID): Task? {
        return tasks.find { it.id == id }
    }

    // mark task as completed
    fun completeTask(taskId: UUID): Task? {
        val task = byId(taskId)
        task?.isCompleted = true
        return task
    }

    // pending tasks
    fun pendingTasks(tasks: List<Task>): List<Task> {
        return tasks.filter { !it.isCompleted }
    }

    // complete tasks
    fun completeTasks(tasks: List<Task>): List<Task> {
        return tasks.filter { it.isCompleted }
    }
}
