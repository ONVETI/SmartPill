package uz.onveti.smartpill.screens.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import uz.onveti.smartpill.screens.main.state.AiMessage
import uz.onveti.smartpill.screens.main.state.MainAction
import uz.onveti.smartpill.screens.main.state.MainModule
import uz.onveti.smartpill.screens.main.state.MainSideEffect
import uz.onveti.smartpill.screens.main.state.MainState

internal class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<MainState, MainSideEffect> {

    override val container: Container<MainState, MainSideEffect> = container(
        initialState = MainState(),
        buildSettings = {
            exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(MainSideEffect.Error(throwable))
                }
            }
        },
        onCreate = {

        },
    )

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.NavigateBack -> onNavigateBackClicked()
            is MainAction.ModuleSelected -> onModuleSelected(action.module)
            is MainAction.AiQuestionChanged -> onAiQuestionChanged(action.value)
            is MainAction.AiQuestionSubmitted -> onAiQuestionSubmitted()
            is MainAction.OpenDrawer -> onOpenDrawer()
            is MainAction.CloseDrawer -> onCloseDrawer()
        }
    }

    private fun onNavigateBackClicked() = intent {
        postSideEffect(MainSideEffect.NavigateBack)
    }

    private fun onModuleSelected(module: MainModule) = intent {
        reduce { state.copy(selectedModule = module) }
        savedStateHandle[SELECTED_MODULE_KEY] = module.name
    }

    private fun onAiQuestionChanged(value: String) = intent {
        reduce { state.copy(aiQuestion = value) }
    }

    private fun onAiQuestionSubmitted() = intent {
        val question = state.aiQuestion.trim()
        if (question.isEmpty()) return@intent

        reduce {
            state.copy(
                aiQuestion = "",
                aiMessages = state.aiMessages + AiMessage(
                    question = question,
                    answer = buildAiAnswer(question),
                ),
            )
        }
    }

    private fun onOpenDrawer() = intent {
        reduce { state.copy(isDrawerOpen = true) }
    }

    private fun onCloseDrawer() = intent {
        reduce { state.copy(isDrawerOpen = false) }
    }

    private fun buildAiAnswer(question: String): String {
        val normalizedQuestion = question.lowercase()

        return when {
            "unut" in normalizedQuestion || "ichmad" in normalizedQuestion ->
                "Smart MedBox AI oxirgi ochilish va qabul tarixiga qarab davoni tashlash xavfini belgilaydi. Dorini qabul qilmagan bo'lsangiz, shifokor ko'rsatmasiga muvofiq keyingi dozani aniqlashtiring."

            "yon" in normalizedQuestion || "ta'sir" in normalizedQuestion ->
                "Nojo'ya ta'sir sezilsa, dorini o'zboshimchalik bilan almashtirmang. Belgilar kuchaysa, Support yoki SOS bo'limi orqali tibbiy xizmatga murojaat qiling."

            "qachon" in normalizedQuestion || "vaqt" in normalizedQuestion ->
                "Qabul vaqti dori yo'riqnomasi va shifokor rejasi asosida nazorat qilinadi. Smart MedBox real ochilish harakatini ko'rib, kechikish xavfini oldindan ko'rsatadi."

            else ->
                "Savolingiz qabul intizomi, dori xavfsizligi yoki davoni davom ettirish bilan bog'liq bo'lsa, Smart MedBox AI holatni tahlil qiladi va xavf belgilarini oldindan ajratib beradi."
        }
    }

    private companion object {
        const val SELECTED_MODULE_KEY = "selected_module"
    }
}
