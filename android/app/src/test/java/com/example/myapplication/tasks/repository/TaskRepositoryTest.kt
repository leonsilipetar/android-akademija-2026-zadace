package com.example.myapplication.tasks.repository

import app.cash.turbine.test
import com.example.myapplication.tasks.data.local.db.TaskDao
import com.example.myapplication.tasks.data.local.db.TaskEntity
import com.example.myapplication.tasks.data.remote.api.TaskieApi
import com.example.myapplication.tasks.data.remote.dto.GetAllTasksResponse
import com.example.myapplication.tasks.data.remote.dto.TaskResponse
import com.example.myapplication.tasks.data.remote.retrofit.RetrofitProvider
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TaskRepositoryTest {

    private lateinit var repository: TaskRepository
    private val taskDao: TaskDao = mockk()
    private val api: TaskieApi = mockk()

    @Before
    fun setup() {
        // Mock the singleton RetrofitProvider API
        mockkObject(RetrofitProvider)
        every { RetrofitProvider.api } returns api
        
        // Initial setup for repository
        every { taskDao.getAllTasks() } returns flowOf(emptyList())
        repository = TaskRepository(taskDao)
    }

    @Test
    fun `tasks flow reflects data from dao`() = runTest {
        val entities = listOf(
            TaskEntity(id = 1, username = "u1", title = "T1", body = "B1")
        )
        every { taskDao.getAllTasks() } returns flowOf(entities)
        
        // Re-init repository to pick up the new flow
        val repo = TaskRepository(taskDao)
        
        repo.tasks.test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("T1", result[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `refreshTasks fetches from api and saves to dao`() = runTest {
        val apiTasks = listOf(
            TaskResponse(id = 1, username = "u1", title = "T1", body = "B1")
        )
        coEvery { api.getTasks() } returns GetAllTasksResponse(apiTasks)
        coEvery { taskDao.getAllTasksSync() } returns emptyList()
        coEvery { taskDao.clearAll() } just Runs
        coEvery { taskDao.insertTasks(any()) } just Runs

        val result = repository.refreshTasks()

        coVerify { api.getTasks() }
        coVerify { taskDao.clearAll() }
        coVerify { taskDao.insertTasks(any()) }
        assertEquals(true, result.isSuccess)
    }
}