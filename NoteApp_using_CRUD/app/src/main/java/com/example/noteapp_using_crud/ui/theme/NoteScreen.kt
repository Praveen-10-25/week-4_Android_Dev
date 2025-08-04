import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp_using_crud.data.Notes
import com.example.noteapp_using_crud.viewmodel.NoteViewModel

@Composable
fun NoteScreen(viewModel: NoteViewModel) {
    val notes by viewModel.notes.observeAsState(emptyList())

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Top) {
        OutlinedTextField(value = title, modifier = Modifier.fillMaxWidth(), onValueChange = { title = it }, label = { Text("Title") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = content, onValueChange = { content = it }, label = { Text("Content") })
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (title.isNotBlank() && content.isNotBlank()) {
                viewModel.addNote(Notes(title = title, content = content))
                title = ""
                content = ""
            }
        }) {
            Text("Add Note")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                        Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { viewModel.deleteNote(note) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}