package com.example.todoappretrofit

data class TodoModel(
    val id: Int,
    val todo: String,
    val completed: Boolean,
    val userId: Int?

)

data class TodosResponse(val todos: List<TodoModel>)
//"id": 1,
//"todo": "Do something nice for someone I care about",
//"completed": true,
//"userId": 26
