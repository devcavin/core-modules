package io.github.devcavin

import io.github.devcavin.jobmgr.Job
import io.github.devcavin.jobmgr.JobStatus
import io.github.devcavin.jobmgr.JobStore
import io.github.devcavin.taskmgr.Task
import io.github.devcavin.taskmgr.TaskService
import java.util.UUID

fun main() {
    val headerText = """
        ===========================================================
                  Core Modules
        ===========================================================
    """.trimIndent()

    println(headerText)

    val task = Task(
        name = "task",
        description = """This is a description.""",
        priority = 1,
        isCompleted = false
    )

    println("""
        Added Task
        ___________________
        $task
    """.trimIndent())

    val taskService = TaskService()

    taskService.addTask(task)
    taskService.addTask(task)
    taskService.addTask(task)
    println(taskService.allTasks())

    taskService.addTask(
        Task(
            id = UUID.fromString("4df9746d-1485-4649-938c-4dd653f4ba22"),
            name = "taskToBeModified",
            description = "This is a modified task.",
            priority = 5
        )
    )

    val task1 = Task(
        name = "task",
        description = """This is a description.""",
        priority = 1,
        isCompleted = true
    )

    taskService.addTask(task1)
    taskService.addTask(task1)
    taskService.addTask(task1)


    println(taskService.byId(UUID.fromString("4df9746d-1485-4649-938c-4dd653f4ba22")))
    println(taskService.completeTask(UUID.fromString("4df9746d-1485-4649-938c-4dd653f4ba22")))

    println(taskService.pendingTasks(taskService.allTasks()))
    println(taskService.completeTasks(taskService.allTasks()))

    val store = JobStore()

    val job = store.addJob(Job("Process files"))

    store.transition(job.id, JobStatus.RUNNING)
    store.transition(job.id, JobStatus.COMPLETED)

    println(store.allJobs())
}