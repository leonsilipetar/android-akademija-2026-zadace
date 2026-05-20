package com.example.myapplication.tasks.viewmodel

import app.cash.turbine.test
import com.example.myapplication.tasks.model.Task
import com.example.myapplication.tasks.repository.TaskRepository
import com.example.myapplication.tasks.ui.state.TaskListUiState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    private val repository: TaskRepository = mockk()
    private val tasksFlow = MutableStateFlow<List<Task>>(emptyList())
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { repository.tasks } returns tasksFlow
        coEvery { repository.refreshTasks() } returns Result.success(Unit)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initially state is Loading than Empty if no tasks`() = runTest {
        val viewModel = ListViewModel(repository)
        
        viewModel.uiState.test {
            // Because of stateIn(initialValue = Loading)
            assertEquals(TaskListUiState.Empty, awaitItem())
        }
    }

    @Test
    fun `state becomes Success when repository has tasks`() = runTest {
        val tasks = listOf(Task(1, "u1", "T1", "B1"))
        tasksFlow.value = tasks
        
        val viewModel = ListViewModel(repository)
        
        viewModel.uiState.test {
            val state = awaitItem()
            assert(state is TaskListUiState.Success)
            assertEquals(1, (state as TaskListUiState.Success).tasks.size)
        }
    }
}