package mega.android.core.ui.components.tooltip.direction

import androidx.compose.runtime.Immutable

@Immutable
sealed interface TooltipDirection {

    data object None : TooltipDirection
    data object Left : TooltipDirection
    data object Right : TooltipDirection

    @Immutable
    sealed interface Top : TooltipDirection {
        data object Left : Top {
            override fun toString(): String = "Top-Left"
        }

        data object Centre : Top {
            override fun toString(): String = "Top-Centre"
        }

        data object Right : Top {
            override fun toString(): String = "Top-Right"
        }
    }

    @Immutable
    sealed interface Bottom : TooltipDirection {
        data object Left : Bottom {
            override fun toString(): String = "Bottom-Left"
        }

        data object Centre : Bottom {
            override fun toString(): String = "Bottom-Centre"
        }

        data object Right : Bottom {
            override fun toString(): String = "Bottom-Right"
        }
    }
}
