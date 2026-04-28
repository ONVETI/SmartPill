package uz.onveti.smartpill.screens.ai_assistant.state

internal data class AiAssistantState(
    val isLoading: Boolean = false,
    val question: String = "",
    val messages: List<AiMessage> = emptyList(),
)

internal data class AiMessage(
    val question: String,
    val answer: String,
)
