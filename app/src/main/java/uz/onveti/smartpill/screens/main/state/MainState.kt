package uz.onveti.smartpill.screens.main.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class MainState(
    val isLoading: Boolean = false,
    val selectedModule: MainModule = MainModule.Medicines,
    val aiQuestion: String = "",
    val aiMessages: List<AiMessage> = emptyList(),
    val isDrawerOpen: Boolean = false,
    val isLanguageDialogVisible: Boolean = false,
)

internal enum class MainModule {
    Medicines,
    AiAssistant,
    Pharmacies,
    SmartWatch,
    Support,
    Sos,
}

@Immutable
internal data class AiMessage(
    val question: String,
    val answer: String,
)
