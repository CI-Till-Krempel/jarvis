import org.junit.Test
import kotlin.test.assertEquals

class SampleTest {
    @Test
    fun testJobCreation() {
        val job = Job("ubuntu", "echo hello", "node1", "Queued")
        assertEquals("ubuntu", job.dockerImage)
        assertEquals("echo hello", job.command)
        assertEquals("node1", job.targetNodeTags)
        assertEquals("Queued", job.status)
    }
}
