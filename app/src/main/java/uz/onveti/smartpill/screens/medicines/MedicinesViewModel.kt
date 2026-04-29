package uz.onveti.smartpill.screens.medicines

import androidx.lifecycle.ViewModel
import uz.onveti.smartpill.data.medicine.MedicineRepository
import uz.onveti.smartpill.notification.MedicineNotificationScheduler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import uz.onveti.smartpill.screens.medicines.state.MedicinesAction
import uz.onveti.smartpill.screens.medicines.state.MedicinesSideEffect
import uz.onveti.smartpill.screens.medicines.state.MedicinesState
import kotlinx.coroutines.CoroutineExceptionHandler

internal class MedicinesViewModel(
    private val medicineRepository: MedicineRepository,
    private val medicineNotificationScheduler: MedicineNotificationScheduler,
) : ViewModel(), ContainerHost<MedicinesState, MedicinesSideEffect> {

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
            observeMedicines()
            observePillStatus()
        },
    )

    fun onAction(action: MedicinesAction) {
        when (action) {
            is MedicinesAction.NavigateBack -> onNavigateBackClicked()
            is MedicinesAction.AddMedicineClicked -> onAddMedicineClicked()
            is MedicinesAction.MedicineClicked -> onMedicineClicked(action.medicineId)
            is MedicinesAction.DeleteMedicineClicked -> onDeleteMedicineClicked(action.medicineId)
        }
    }

    private fun observeMedicines() = intent {
        reduce { state.copy(isLoading = true) }
        medicineRepository.observeMedicines().collect { medicines ->
            reduce {
                state.copy(
                    isLoading = false,
                    medicines = medicines,
                )
            }
        }
    }

    private fun onNavigateBackClicked() = intent {
        postSideEffect(MedicinesSideEffect.NavigateBack)
    }

    private fun observePillStatus() = intent {
        medicineRepository.observePillStatus().collect { pillStatus ->
            val normalizedStatus = pillStatus?.trim()
            if (normalizedStatus != state.lastPillStatus) {
                reduce {
                    state.copy(
                        lastPillStatus = normalizedStatus,
                    )
                }
            }
        }
    }

    private fun onAddMedicineClicked() = intent {
        postSideEffect(MedicinesSideEffect.NavigateToAddMedicine)
    }

    private fun onMedicineClicked(medicineId: String) = intent {
        postSideEffect(MedicinesSideEffect.NavigateToMedicineDetail(medicineId))
    }

    private fun onDeleteMedicineClicked(medicineId: String) = intent {
        val medicine = state.medicines.firstOrNull { item -> item.id == medicineId }
        if (medicine != null) {
            medicineNotificationScheduler.cancel(medicine)
        }
        medicineRepository.deleteMedicine(medicineId)
    }
}
