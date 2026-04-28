package uz.onveti.smartpill.screens.sos

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import uz.onveti.smartpill.screens.sos.state.SosAction
import uz.onveti.smartpill.screens.sos.state.SosSideEffect
import uz.onveti.smartpill.screens.sos.state.SosState
import kotlinx.coroutines.CoroutineExceptionHandler

internal class SosViewModel : ViewModel(), ContainerHost<SosState, SosSideEffect> {

    override val container: Container<SosState, SosSideEffect> = container(
        initialState = SosState(),
        buildSettings = {
            exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(SosSideEffect.Error(throwable))
                }
            }
        },
        onCreate = {

        },
    )

    fun onAction(action: SosAction) {
        when (action) {
            is SosAction.NavigateBack -> onNavigateBackClicked()
        }
    }

    private fun onNavigateBackClicked() = intent {
        postSideEffect(SosSideEffect.NavigateBack)
    }
}
