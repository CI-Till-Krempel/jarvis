import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
}

@Composable
@Preview
fun App() {
    var dockerImage by remember { mutableStateOf("") }
    var command by remember { mutableStateOf("") }
    var targetNodeTags by remember { mutableStateOf("") }
    var jobs by remember { mutableStateOf(listOf<Job>()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            // Poll for jobs (mocked for now, but uses the client)
            try {
                // val response = client.get("http://control-server/jobs")
                // jobs = response.body()
                delay(5000)
            } catch (e: Exception) {
                // handle error
            }
        }
    }

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
                coroutineScope.launch {
                    try {
                         client.post("http://control-server/submit") {
                             // setBody(newJob)
                         }
                    } catch (e: Exception) {
                        // Handle error
                    }
                }
                
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
