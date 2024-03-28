package mega.android.core.ui.theme.font

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import mega.android.core.ui.R

private val googleFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val WorkSansFont = GoogleFont("Work Sans")

private val WorkSansFontFamily = FontFamily(
    Font(googleFont = WorkSansFont, fontProvider = googleFontProvider),
    Font(googleFont = WorkSansFont, fontProvider = googleFontProvider, weight = FontWeight.Medium),
    Font(googleFont = WorkSansFont, fontProvider = googleFontProvider, weight = FontWeight.Bold)
)

data class GoogleFontFamily(
    val workSans: FontFamily = WorkSansFontFamily
)