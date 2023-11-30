package mega.android.core.ui.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mega.android.core.ui.components.DefaultDivider
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.SuccessFooter
import mega.android.core.ui.theme.tokens.TextColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column {
        MegaText(
            text = "Hello $name!",
            textColor = TextColor.Secondary
        )
        DefaultDivider()
        SuccessFooter(text = stringResource(id = R.string.app_name))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
        Greeting("Android")
}