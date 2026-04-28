package uz.onveti.smartpill.screens.main.state

internal sealed interface MainAction {

    data object NavigateBack : MainAction
    data class ModuleSelected(val module: MainModule) : MainAction
    data class AiQuestionChanged(val value: String) : MainAction
    data object AiQuestionSubmitted : MainAction
    data object OpenDrawer : MainAction
    data object CloseDrawer : MainAction
}
