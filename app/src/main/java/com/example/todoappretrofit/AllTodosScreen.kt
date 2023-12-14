import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoappretrofit.MainViewModel
import com.example.todoappretrofit.TodoModel


@Composable
fun AllTodosScreen(modifier: Modifier = Modifier) {
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
                AllTodosDisplay(todos = viewstate.list)
            }
        }
    }

}

@Composable
fun AllTodosDisplay(todos : List<TodoModel>) {
    LazyColumn(modifier = Modifier.fillMaxSize() ){
        items(todos){
            item: TodoModel ->
            TodoItem(todoItem = item)
        }
    }
}

@Composable
fun TodoItem(todoItem: TodoModel ) {

    Row (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = todoItem.id.toString()+". ",
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier
            )

        Text(
            text = todoItem.todo,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Medium),
//            modifier = Modifier.padding(start = 4.dp)
        )

        Checkbox(checked = todoItem.completed, onCheckedChange = null )

    }

}