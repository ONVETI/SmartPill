package uz.onveti.smartpill.screens.add_medicine

import androidx.lifecycle.ViewModel
import uz.onveti.smartpill.data.medicine.MedicineRepository
import uz.onveti.smartpill.notification.MedicineNotificationScheduler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import uz.onveti.smartpill.screens.add_medicine.state.AddMedicineAction
import uz.onveti.smartpill.screens.add_medicine.state.AddMedicineSideEffect
import uz.onveti.smartpill.screens.add_medicine.state.AddMedicineState
import kotlinx.coroutines.CoroutineExceptionHandler

internal class AddMedicineViewModel(
    private val medicineRepository: MedicineRepository,
    private val medicineNotificationScheduler: MedicineNotificationScheduler,
) : ViewModel(), ContainerHost<AddMedicineState, AddMedicineSideEffect> {

    override val container: Container<AddMedicineState, AddMedicineSideEffect> = container(
        initialState = AddMedicineState(),
        buildSettings = {
            exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(AddMedicineSideEffect.Error(throwable))
                }
            }
        },
        onCreate = {

        },
    )

    fun onAction(action: AddMedicineAction) {
        when (action) {
            is AddMedicineAction.NavigateBack -> onNavigateBackClicked()
            is AddMedicineAction.NameChanged -> intent {
                reduce { state.copy(name = action.value, validationMessage = null) }
            }
            is AddMedicineAction.MorningTimeChanged -> intent {
                reduce { state.copy(morningTime = action.value, validationMessage = null) }
            }
            is AddMedicineAction.AfternoonTimeChanged -> intent {
                reduce { state.copy(afternoonTime = action.value, validationMessage = null) }
            }
            is AddMedicineAction.EveningTimeChanged -> intent {
                reduce { state.copy(eveningTime = action.value, validationMessage = null) }
            }
            is AddMedicineAction.MealTimingChanged -> intent {
                reduce { state.copy(mealTiming = action.value, validationMessage = null) }
            }
            is AddMedicineAction.SaveClicked -> onSaveClicked()
        }
    }

    private fun onNavigateBackClicked() = intent {
        postSideEffect(AddMedicineSideEffect.NavigateBack)
    }

    private fun onSaveClicked() = intent {
        val currentState = state

        if (currentState.name.isBlank()) {
            reduce { state.copy(validationMessage = "Dori nomini kiriting") }
            return@intent
        }

        if (
            currentState.morningTime.isBlank() &&
            currentState.afternoonTime.isBlank() &&
            currentState.eveningTime.isBlank()
        ) {
            reduce { state.copy(validationMessage = "Kamida bitta mahal soatini tanlang") }
            return@intent
        }

        reduce { state.copy(isLoading = true, validationMessage = null) }
        val medicine = medicineRepository.addMedicine(
            name = currentState.name.trim(),
            morningTime = currentState.morningTime.trim(),
            afternoonTime = currentState.afternoonTime.trim(),
            eveningTime = currentState.eveningTime.trim(),
            mealTiming = currentState.mealTiming,
        )
        medicineNotificationScheduler.schedule(medicine)
        reduce { state.copy(isLoading = false) }
        postSideEffect(AddMedicineSideEffect.Saved)
    }
}
