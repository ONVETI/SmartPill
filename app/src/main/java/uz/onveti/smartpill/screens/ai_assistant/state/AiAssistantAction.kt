package uz.onveti.smartpill.screens.ai_assistant.state

internal sealed interface AiAssistantAction {

    data object NavigateBack : AiAssistantAction
    data class QuestionChanged(val value: String) : AiAssistantAction
    data object QuestionSubmitted : AiAssistantAction

}
