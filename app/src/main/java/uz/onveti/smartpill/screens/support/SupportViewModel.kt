package uz.onveti.smartpill.screens.support

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import uz.onveti.smartpill.screens.support.state.SupportAction
import uz.onveti.smartpill.screens.support.state.SupportSideEffect
import uz.onveti.smartpill.screens.support.state.SupportState
import kotlinx.coroutines.CoroutineExceptionHandler

internal class SupportViewModel : ViewModel(), ContainerHost<SupportState, SupportSideEffect> {

    override val container: Container<SupportState, SupportSideEffect> = container(
        initialState = SupportState(),
        buildSettings = {
            exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(SupportSideEffect.Error(throwable))
                }
            }
        },
        onCreate = {

        },
    )

    fun onAction(action: SupportAction) {
        when (action) {
            is SupportAction.NavigateBack -> onNavigateBackClicked()
        }
    }

    private fun onNavigateBackClicked() = intent {
        postSideEffect(SupportSideEffect.NavigateBack)
    }
}
