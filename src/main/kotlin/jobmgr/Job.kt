package io.github.devcavin.jobmgr

import java.util.*

data class Job(
    val id: UUID,
    val name: String,
    val status: JobStatus
) {
    constructor(name: String) : this(
        id = UUID.randomUUID(),
        name = name,
        status = JobStatus.CREATED
    )

    fun withStatus(newStatus: JobStatus): Job =
        copy(status = newStatus)
}


enum class JobStatus {
    CREATED,
    RUNNING,
    COMPLETED,
    FAILED
}

object JobTransitions {

    private val rules: Map<JobStatus, Set<JobStatus>> = mapOf(
        JobStatus.CREATED to setOf(JobStatus.RUNNING),
        JobStatus.RUNNING to setOf(
            JobStatus.COMPLETED,
            JobStatus.FAILED
        )
    )

    fun isAllowed(from: JobStatus, to: JobStatus): Boolean =
        rules[from]?.contains(to) ?: false
}


class JobStore(
    private val jobs: MutableList<Job> = mutableListOf()
) {

    fun allJobs(): List<Job> = jobs

    fun addJob(job: Job): Job {
        jobs.add(job)
        return job
    }

    fun byId(id: UUID): Job? =
        jobs.find { it.id == id }

    fun transition(jobId: UUID, targetStatus: JobStatus): Job {
        val current = byId(jobId)
            ?: error("Job not found")

        if (!JobTransitions.isAllowed(current.status, targetStatus)) {
            error("Cannot move from ${current.status} to $targetStatus")
        }

        val updated = current.withStatus(targetStatus)

        jobs.remove(current)
        jobs.add(updated)

        return updated
    }
}
