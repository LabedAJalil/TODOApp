package com.labed.todo.data.repository

import androidx.lifecycle.LiveData
import com.labed.todo.data.ToDoDao
import com.labed.todo.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()
    val sortByHighPriority = toDoDao.sortByHighPriority()
    val sortByLowPriority = toDoDao.sortByLowPriority()

    suspend fun insertToDoData(toDoData: ToDoData) {
        toDoDao.insertToDoData(toDoData)
    }

    suspend fun updateToDoData(toDoData: ToDoData) {
        toDoDao.updateTodoData(toDoData)
    }

    suspend fun deleteToDoData(toDoData: ToDoData) {
        toDoDao.deleteTodoData(toDoData)
    }

    suspend fun deleteAll() {
        toDoDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>> {
        return toDoDao.searchDatabase(searchQuery)
    }



}