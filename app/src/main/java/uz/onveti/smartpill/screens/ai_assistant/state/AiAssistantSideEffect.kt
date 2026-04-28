package uz.onveti.smartpill.screens.ai_assistant.state

import kotlin.jvm.JvmInline

internal sealed interface AiAssistantSideEffect {

    data object NavigateBack : AiAssistantSideEffect

    @JvmInline
    value class Error(val throwable: Throwable) : AiAssistantSideEffect
}
