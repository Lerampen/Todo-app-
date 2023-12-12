package com.example.todoappretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoappretrofit.ui.theme.TodoAppRetrofitTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppRetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    MyApp ()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
 PostTodoScreen()

}

@Composable
fun PostTodoScreen(viewModel: MainViewModel = viewModel()) {
    var isDialogVisible  by remember {
        mutableStateOf(false)
    }
    var newTodoTitle by remember {
        mutableStateOf("")
    }
    var newTodoBody by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = Unit ){
        viewModel.fetchTodos()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
//        Show the list of todos
        AllTodosScreen(viewModel.todos.value)
        
        FloatingActionButton(onClick = { isDialogVisible = true },
            modifier = Modifier
                .padding(16.dp)
                .size(56.dp)
        ) {
           Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }

//        Dialog for adding a newtodo
        if (isDialogVisible){
            AddTodoDialog(
                onDismiss = {
                    isDialogVisible = false
                    newTodoTitle = ""
                    newTodoBody = ""
                },
                onSave = {
                    viewModel.postTodo(
                        TodoModel(
                            id = Random.nextInt(),
                            title = newTodoTitle,
                            body = newTodoBody,
                            userId = null //Since userId is randomly generated in postTodo
                        )
                    )
                    isDialogVisible = false
                    newTodoTitle = ""
                    newTodoBody = ""
                },
                titleValue = newTodoTitle,
                onTitleChange = {newTodoTitle = it},
                bodyValue = newTodoBody,
                onBodyChange = {newTodoBody = it}
            )

        }

        
    }



}

@Composable
fun AddTodoDialog(
    onDismiss: () -> Unit,
    onSave: (TodoModel) -> Unit,
    titleValue : String,
    onTitleChange : (String) -> Unit,
    bodyValue: String,
    onBodyChange: (String) -> Unit
) {
    var isSaving by remember { mutableStateOf(false) }


    Dialog(onDismissRequest = {
        if (!isSaving) {
            onDismiss()
        }
    }
    ) {


        Surface(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                // Title TextField
                OutlinedTextField(
                    value = titleValue,
                    onValueChange = onTitleChange,
                    label = { Text("Title") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                // Body TextField
                OutlinedTextField(
                    value = bodyValue,
                    onValueChange = onBodyChange,
                    label = { Text("Body") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                //Save Button
                Button(onClick = {
                    isSaving = true
                    onSave(
                        TodoModel(
                            id = Random.nextInt(),
                            title = titleValue,
                            body = bodyValue,
                            userId = null // Since userId is randomly generated in postTodo
                        )
                    )
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(8.dp),
                    enabled = !isSaving
                )
                 {
                    if (isSaving){
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                    }else{
                        Icon(imageVector = Icons.Default.Done, contentDescription = "SAVE")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Save Todo")
                    }

                }

            }
        }

    }

}

@Composable
fun AllTodosScreen(todos: List<TodoModel>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
        
        
    ){
        items(todos){todo->
            TodoListItem(todo)
        }
    }

}

@Composable
fun TodoListItem(todo: TodoModel) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "${todo.id}. ${todo.body.take(15)}...",// show snippet of the body
            style = MaterialTheme.typography.bodyMedium
            )
        Spacer(modifier = Modifier.width(8.dp))

        Checkbox(checked = false,
        onCheckedChange ={}
        )

    }
}
