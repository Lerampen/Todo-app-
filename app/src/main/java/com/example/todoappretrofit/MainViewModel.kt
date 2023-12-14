package com.example.todoappretrofit

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import kotlin.random.Random





class MainViewModel : ViewModel() {

    data class TodosUiState(
        val loading: Boolean = true,
        val list: List<TodoModel> = emptyList(),
        val error: String? = null
    )


    private val _todosState = mutableStateOf(TodosUiState())
    val todosState: State<TodosUiState> = _todosState


    init {
        fetchTodos()
    }


    private fun fetchTodos() {
        viewModelScope.launch {
            // val result = TodoPostApi.retrofitService.getTodos()
            try {
                val response = TodoPostApi.retrofitService.getTodos()
                _todosState.value = _todosState.value.copy(
                    loading = false,
                    list = response.todos,
                    error = null
                )
            } catch (e: Exception) {
                _todosState.value = _todosState.value.copy(
                    loading = false,
                    error = "Error fetching the Todos ${e.message}"
                )
            }


        }
    }
//id: Int,
//    val todo: String,
//    val completed: Boolean,
//    val userId:
var todoString = ""
    var completedTask = false
    var userId = Random.nextInt(1,50)


    fun postTodos(){
        viewModelScope.launch {

            val toDo = TodoModel(id = 31, todo = todoString, completed = completedTask, userId= userId)

            try {

                val response = TodoPostApi.retrofitService.postTodo(toDo)
                _todosState.value = _todosState.value.copy(
                    loading = false,
                    list = response.todos,
                    error = null
                )

            }catch (e: Exception){
                _todosState.value = _todosState.value.copy(
                    loading = false,
                    error = "Error posting the Todos ${e.message}"
                )
            }


        }
    }


}




//Coroutine  function to post a new todo
//suspend fun postTodo(todo: TodoModel) {
////    set loading to true while posting data
//
//
//    viewModelScope.launch {
//        try {
////            Simulate a delay to show loading state
//            delay(2000)
//
////            Generate a random user
////ID for the new todo
//            val randomUserId = Random.nextInt(1, 100)
//             withContext(Dispatchers.IO) {
//                TodoPostApi.retrofitService.postTodo(todo)
//            }
//
////            Update the todo list with the new todo and random user ID
//            _todos.value = _todos.value + todo.copy(userId = randomUserId)
//
//        } catch (e: Exception) {
//            // Log any errors that occur during the posting process
//            e.printStackTrace()
//            Log.e("TodoViewModel", "Error adding todo: ${e.message}")
//        } finally {
//            // Set loading to false after posting is complete
//            _isLoading.value = false
//
//        }
//    }
//}
//
//
//}
