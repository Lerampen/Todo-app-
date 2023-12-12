package com.example.todoappretrofit
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


private const val BASE_URL = "https://jsonplaceholder.typicode.com"


//creating a retrofit builder
private val  retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @POST("/posts")
    suspend fun postTodo(@Body todo: TodoModel): Response<TodoModel>

    @GET("/1/todos")
    suspend fun getTodos(): List<TodoModel>
}
object TodoPostApi{
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}