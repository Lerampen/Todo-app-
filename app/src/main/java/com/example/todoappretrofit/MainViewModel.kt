package com.example.todoappretrofit

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

    private val _postResult = mutableStateOf<PostResult?>(null)
    val postResult: State<PostResult?> = _postResult

    private val _todos = mutableStateOf<List<TodoModel>>(emptyList())
    val todos: State<List<TodoModel>> = _todos

    fun postTodo(todo: TodoModel){
        viewModelScope.launch {
//            Stimulate a delay to mimic a network call
            delay(2000)


//            Generate a random UserId for the newtodo
            val randomUserId = Random.nextInt(1, 100)
//            Update the postResult
            try {
                val response = withContext(Dispatchers.IO) {
                    TodoPostApi.retrofitService.postTodo(todo)
                }
                if (response.isSuccessful) {
                    _postResult.value = PostResult.Success
                    // Handle successful response
                } else {
                    val errorResult = PostResult.Error("Failed to post todo. Please try again.")
                    // Log or handle error
                }
            } catch (e: Exception) {
//                _postResult.value = PostResult.Error(e.message ?: "Unknown error")
                // Log or handle exception
              e.printStackTrace()

            }


//            Add the posted atodo to the list with the random userId
            _todos.value = _todos.value + todo.copy(userId = randomUserId)
        }
    }

    suspend fun fetchTodos(){
//        Simulate a network call delay
        delay(1000)

//        Fetch todos from the server
        _todos.value = TodoPostApi.retrofitService.getTodos()
    }

}