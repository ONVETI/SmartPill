package uz.onveti.smartpill.screens.medicine_detail

import androidx.lifecycle.ViewModel
import uz.onveti.smartpill.data.medicine.MedicineRepository
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import uz.onveti.smartpill.screens.medicine_detail.state.MedicineDetailAction
import uz.onveti.smartpill.screens.medicine_detail.state.MedicineDetailSideEffect
import uz.onveti.smartpill.screens.medicine_detail.state.MedicineDetailState
import kotlinx.coroutines.CoroutineExceptionHandler

internal class MedicineDetailViewModel(
    private val medicineRepository: MedicineRepository,
) : ViewModel(), ContainerHost<MedicineDetailState, MedicineDetailSideEffect> {

    private var loadedMedicineId: String? = null

    override val container: Container<MedicineDetailState, MedicineDetailSideEffect> = container(
        initialState = MedicineDetailState(),
        buildSettings = {
            exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(MedicineDetailSideEffect.Error(throwable))
                }
            }
        },
        onCreate = {

        },
    )

    fun onAction(action: MedicineDetailAction) {
        when (action) {
            is MedicineDetailAction.NavigateBack -> onNavigateBackClicked()
            is MedicineDetailAction.LoadMedicine -> onLoadMedicine(action.medicineId)
        }
    }

    private fun onLoadMedicine(medicineId: String) {
        if (loadedMedicineId == medicineId) return
        loadedMedicineId = medicineId

        intent {
            reduce { state.copy(isLoading = true) }
            medicineRepository.observeMedicine(medicineId).collect { medicine ->
                reduce {
                    state.copy(
                        isLoading = false,
                        medicine = medicine,
                    )
                }
            }
        }
    }

    private fun onNavigateBackClicked() = intent {
        postSideEffect(MedicineDetailSideEffect.NavigateBack)
    }
}
