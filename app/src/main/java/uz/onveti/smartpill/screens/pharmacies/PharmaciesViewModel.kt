package uz.onveti.smartpill.screens.pharmacies

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import uz.onveti.smartpill.screens.pharmacies.state.PharmaciesAction
import uz.onveti.smartpill.screens.pharmacies.state.PharmaciesSideEffect
import uz.onveti.smartpill.screens.pharmacies.state.PharmaciesState
import kotlinx.coroutines.CoroutineExceptionHandler

internal class PharmaciesViewModel : ViewModel(), ContainerHost<PharmaciesState, PharmaciesSideEffect> {

    override val container: Container<PharmaciesState, PharmaciesSideEffect> = container(
        initialState = PharmaciesState(),
        buildSettings = {
            exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(PharmaciesSideEffect.Error(throwable))
                }
            }
        },
        onCreate = {

        },
    )

    fun onAction(action: PharmaciesAction) {
        when (action) {
            is PharmaciesAction.NavigateBack -> onNavigateBackClicked()
        }
    }

    private fun onNavigateBackClicked() = intent {
        postSideEffect(PharmaciesSideEffect.NavigateBack)
    }
}
