package com.example.todoappretrofit

sealed class PostResult {
    object Success : PostResult()
//    object Error: PostResult()
data class Error(val errorMessage: String)
}