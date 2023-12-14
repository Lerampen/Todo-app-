package com.example.todoappretrofit
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


private const val BASE_URL = "https://dummyjson.com/"


//creating a retrofit builder
private val  retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @POST("todos/add")
    suspend fun postTodo(@Body todo: TodoModel): TodosResponse

    @GET("todos")
    suspend fun getTodos(): TodosResponse
}
object TodoPostApi{
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}