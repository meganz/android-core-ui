package mega.android.core.ui.tokens.theme.spacing

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val spacings = Spacings()

@Immutable
data class Spacings(
    val s1: Dp = 2.dp,
    val s2: Dp = 4.dp,
    val s3: Dp = 8.dp,
    val s4: Dp = 12.dp,
    val s5: Dp = 16.dp,
    val s6: Dp = 20.dp,
    val s7: Dp = 24.dp,
    val s8: Dp = 28.dp,
    val s9: Dp = 32.dp,
    val s10: Dp = 36.dp,
)