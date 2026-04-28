package uz.onveti.smartpill.screens.medicines

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import uz.onveti.smartpill.screens.medicines.state.MedicinesAction
import uz.onveti.smartpill.screens.medicines.state.MedicinesSideEffect
import uz.onveti.smartpill.screens.medicines.state.MedicinesState
import kotlinx.coroutines.CoroutineExceptionHandler

internal class MedicinesViewModel : ViewModel(), ContainerHost<MedicinesState, MedicinesSideEffect> {

    override val container: Container<MedicinesState, MedicinesSideEffect> = container(
        initialState = MedicinesState(),
        buildSettings = {
            exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(MedicinesSideEffect.Error(throwable))
                }
            }
        },
        onCreate = {

        },
    )

    fun onAction(action: MedicinesAction) {
        when (action) {
            is MedicinesAction.NavigateBack -> onNavigateBackClicked()
        }
    }

    private fun onNavigateBackClicked() = intent {
        postSideEffect(MedicinesSideEffect.NavigateBack)
    }
}
