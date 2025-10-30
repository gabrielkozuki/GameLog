package com.example.gamelog.screens.app.gamelist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.base.Routes
import com.example.gamelog.data.model.gamelist.GameList
import com.example.gamelog.data.model.gamelist.Task
import com.example.gamelog.data.repository.GameListRepository
import java.util.UUID
import kotlin.text.get
import kotlin.text.set
import kotlin.toString

class GameListDetailViewModel(val navController: NavController): ViewModel() {

    var gameList by mutableStateOf<GameList?>(null)
        private set

    val tasks = mutableStateListOf<Task>()
    var isLoading by mutableStateOf(true)
    var newTaskTitle by mutableStateOf("")

    fun loadGameList(gameListId: String) {
        isLoading = true
        GameListRepository.getGameList(gameListId) { loadedGameList ->
            gameList = loadedGameList
            loadedGameList?.let {
                tasks.clear()
                tasks.addAll(it.tasks)
            }
            isLoading = false
        }
    }

    fun addTask() {
        if (newTaskTitle.isBlank()) return

        val newTask = Task(
            id = UUID.randomUUID().toString(),
            title = newTaskTitle,
            completed = false
        )

        tasks.add(newTask)
        newTaskTitle = ""
        saveTasks()
    }

    fun toggleTask(taskId: String) {
        val taskIndex = tasks.indexOfFirst { it.id == taskId }
        if (taskIndex != -1) {
            val updatedTask = tasks[taskIndex].copy(completed = !tasks[taskIndex].completed)
            tasks[taskIndex] = updatedTask

            val currentGameList = gameList ?: return
            val updatedGameList = currentGameList.copy(tasks = tasks.toList())
            gameList = updatedGameList

            GameListRepository.updateGameList(updatedGameList)
        }
    }


    private fun saveTasks() {
        val currentGameList = gameList ?: return
        val updatedGameList = currentGameList.copy(tasks = tasks.toList())
        gameList = updatedGameList
        GameListRepository.updateGameList(updatedGameList)
    }

    fun deleteTask(taskId: String) {
        tasks.removeAll { it.id == taskId }
        saveTasks()
    }

    fun navigateToEdit() {
        gameList?.id?.let { id ->
            navController.navigate(Routes.GameList.editRoute(id))
        }
    }
}