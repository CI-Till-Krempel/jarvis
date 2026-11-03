import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    var dockerImage by remember { mutableStateOf("") }
    var command by remember { mutableStateOf("") }
    var targetNodeTags by remember { mutableStateOf("") }
    var jobs by remember { mutableStateOf(listOf<Job>()) }

    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Admin Dashboard Job Submission", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = dockerImage,
                onValueChange = { dockerImage = it },
                label = { Text("Docker Image") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = command,
                onValueChange = { command = it },
                label = { Text("Command") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = targetNodeTags,
                onValueChange = { targetNodeTags = it },
                label = { Text("Target Node Tags") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val newJob = Job(dockerImage, command, targetNodeTags, "Queued")
                jobs = jobs + newJob
                // Reset fields
                dockerImage = ""
                command = ""
                targetNodeTags = ""
            }) {
                Text("Submit Job")
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text("Job Pipeline View", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(16.dp))
            jobs.forEach { job ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Image: ${job.dockerImage}")
                        Text("Command: ${job.command}")
                        Text("Tags: ${job.targetNodeTags}")
                        Text("Status: ${job.status}")
                    }
                }
            }
        }
    }
}

data class Job(
    val dockerImage: String,
    val command: String,
    val targetNodeTags: String,
    val status: String
)

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Admin Dashboard") {
        App()
    }
}
