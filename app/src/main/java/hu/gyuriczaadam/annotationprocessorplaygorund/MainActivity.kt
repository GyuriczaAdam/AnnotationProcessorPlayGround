package hu.gyuriczaadam.annotationprocessorplaygorund

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.gyuriczaadam.annotationprocessorplaygorund.ui.theme.AnnotationProcessorPlaygorundTheme
import hu.gyuriczaadam.annotationprocessplaygorund.tags.TestTags
import hu.gyuriczaadam.marker.marker

@marker
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnnotationProcessorPlaygorundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android: ${TestTags.Homework}")
                }
            }
        }
    }
}

@marker
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnnotationProcessorPlaygorundTheme {
        Greeting("AndroidTag.Button")
    }
}