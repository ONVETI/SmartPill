package uz.onveti.smartpill.screens.ai_assistant

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import uz.onveti.smartpill.screens.ai_assistant.state.AiAssistantAction
import uz.onveti.smartpill.screens.ai_assistant.state.AiMessage
import uz.onveti.smartpill.screens.ai_assistant.state.AiAssistantSideEffect
import uz.onveti.smartpill.screens.ai_assistant.state.AiAssistantState
import kotlinx.coroutines.CoroutineExceptionHandler

internal class AiAssistantViewModel : ViewModel(), ContainerHost<AiAssistantState, AiAssistantSideEffect> {

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
        if (question.isEmpty()) return@intent

        reduce {
            state.copy(
                question = "",
                messages = state.messages + AiMessage(
                    question = question,
                    answer = buildAiAnswer(question),
                ),
            )
        }
    }

    private fun buildAiAnswer(question: String): String {
        val normalizedQuestion = question.lowercase()

        return when {
            "unut" in normalizedQuestion || "ichmad" in normalizedQuestion ->
                "Smart MedBox AI oxirgi ochilish va qabul tarixiga qarab davoni tashlash xavfini belgilaydi. Dorini qabul qilmagan bo‘lsangiz, shifokor ko‘rsatmasiga muvofiq keyingi dozani aniqlashtiring."

            "yon" in normalizedQuestion || "ta’sir" in normalizedQuestion || "ta'sir" in normalizedQuestion ->
                "Nojo‘ya ta’sir sezilsa, dorini o‘zboshimchalik bilan almashtirmang. Belgilar kuchaysa, Support yoki SOS bo‘limi orqali tibbiy xizmatga murojaat qiling."

            "qachon" in normalizedQuestion || "vaqt" in normalizedQuestion ->
                "Qabul vaqti dori yo‘riqnomasi va shifokor rejasi asosida nazorat qilinadi. Smart MedBox real ochilish harakatini ko‘rib, kechikish xavfini oldindan ko‘rsatadi."

            else ->
                "Savolingiz qabul intizomi, dori xavfsizligi yoki davoni davom ettirish bilan bog‘liq bo‘lsa, Smart MedBox AI holatni tahlil qiladi va xavf belgilarini oldindan ajratib beradi."
        }
    }
}
