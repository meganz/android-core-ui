package mega.android.core.ui.theme.spacing

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val x2: Dp = 2.dp,
    val x4: Dp = 4.dp,
    val x8: Dp = 8.dp,
    val x12: Dp = 12.dp,
    val x16: Dp = 16.dp,
    val x20: Dp = 20.dp,
    val x24: Dp = 24.dp,
    val x28: Dp = 28.dp,
    val x32: Dp = 32.dp,
    val x36: Dp = 36.dp,
    val x40: Dp = 40.dp,
    val x44: Dp = 44.dp,
    val x48: Dp = 48.dp,
    val x56: Dp = 56.dp,
    val x64: Dp = 64.dp,
    val x72: Dp = 72.dp,
    val x80: Dp = 80.dp,
)

val LocalSpacing = compositionLocalOf { Dimensions() }
