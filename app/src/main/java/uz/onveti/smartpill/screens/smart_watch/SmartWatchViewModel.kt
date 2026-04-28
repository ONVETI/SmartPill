package uz.onveti.smartpill.screens.smart_watch

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import uz.onveti.smartpill.screens.smart_watch.state.SmartWatchAction
import uz.onveti.smartpill.screens.smart_watch.state.SmartWatchSideEffect
import uz.onveti.smartpill.screens.smart_watch.state.SmartWatchState
import kotlinx.coroutines.CoroutineExceptionHandler

internal class SmartWatchViewModel : ViewModel(), ContainerHost<SmartWatchState, SmartWatchSideEffect> {

    override val container: Container<SmartWatchState, SmartWatchSideEffect> = container(
        initialState = SmartWatchState(),
        buildSettings = {
            exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(SmartWatchSideEffect.Error(throwable))
                }
            }
        },
        onCreate = {

        },
    )

    fun onAction(action: SmartWatchAction) {
        when (action) {
            is SmartWatchAction.NavigateBack -> onNavigateBackClicked()
        }
    }

    private fun onNavigateBackClicked() = intent {
        postSideEffect(SmartWatchSideEffect.NavigateBack)
    }
}
