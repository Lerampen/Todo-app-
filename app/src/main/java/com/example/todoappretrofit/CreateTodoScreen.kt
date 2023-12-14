import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoappretrofit.MainViewModel

@Composable
fun CreateTodoScreen(modifier: Modifier = Modifier) {

    val todoViewModel: MainViewModel = viewModel()
    val viewstate by todoViewModel.todosState

    Box(modifier = Modifier.fillMaxSize()) {
        when{
            viewstate.loading ->{
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }
            viewstate.error != null ->{
                Text(text = "ERROR OCCURRED!", color = Color.Red)
            }else ->{
            //Display Categories
                AddaTodo()
           
        }
        }
    }
    
}

@Composable
fun AddaTodo() {
    val todoViewModel: MainViewModel = viewModel()

    Column(modifier = Modifier.fillMaxSize()) {

       OutlinedTextField(
           value = todoViewModel.todoString,
           onValueChange = {
               todoViewModel.todoString = it
           },

       )
        Button(onClick = { todoViewModel.postTodos() },
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = "Save")
        }
    }
}