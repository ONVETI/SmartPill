package uz.onveti.smartpill.screens.ai_assistant

import androidx.lifecycle.ViewModel
import uz.onveti.smartpill.data.ai.OpenAiRepository
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import uz.onveti.smartpill.screens.ai_assistant.state.AiAssistantAction
import uz.onveti.smartpill.screens.ai_assistant.state.AiMessage
import uz.onveti.smartpill.screens.ai_assistant.state.AiAssistantSideEffect
import uz.onveti.smartpill.screens.ai_assistant.state.AiAssistantState
import kotlinx.coroutines.CoroutineExceptionHandler

internal class AiAssistantViewModel(
    private val openAiRepository: OpenAiRepository,
) : ViewModel(), ContainerHost<AiAssistantState, AiAssistantSideEffect> {

    override val container: Container<AiAssistantState, AiAssistantSideEffect> = container(
        initialState = AiAssistantState(),
        buildSettings = {
            exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(AiAssistantSideEffect.Error(throwable))
                }
            }
        },
        onCreate = {

        },
    )

    fun onAction(action: AiAssistantAction) {
        when (action) {
            is AiAssistantAction.NavigateBack -> onNavigateBackClicked()
            is AiAssistantAction.QuestionChanged -> onQuestionChanged(action.value)
            is AiAssistantAction.QuestionSubmitted -> onQuestionSubmitted()
        }
    }

    private fun onNavigateBackClicked() = intent {
        postSideEffect(AiAssistantSideEffect.NavigateBack)
    }

    private fun onQuestionChanged(value: String) = intent {
        reduce { state.copy(question = value) }
    }

    private fun onQuestionSubmitted() = intent {
        val question = state.question.trim()
        if (question.isEmpty() || state.isLoading) return@intent

        reduce {
            state.copy(
                isLoading = true,
                question = "",
            )
        }

        val answer = openAiRepository.askMedicalQuestion(question)
        reduce {
            state.copy(
                isLoading = false,
                messages = state.messages + AiMessage(
                    question = question,
                    answer = answer,
                ),
            )
        }
    }
}
