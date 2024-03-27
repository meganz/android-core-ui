package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.slider.MegaSlider
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun SliderComponentCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))
    Section(header = "Slider") {
        var sliderValue by remember { mutableStateOf(0f) }
        MegaSlider(
            value = sliderValue,
            onValueChange = {
                sliderValue = it
            }
        )
    }
}
