package uz.onveti.smartpill.screens.medicines.state

import kotlin.jvm.JvmInline

internal sealed interface MedicinesSideEffect {

    data object NavigateBack : MedicinesSideEffect

    data object NavigateToAddMedicine : MedicinesSideEffect

    data class NavigateToMedicineDetail(val medicineId: String) : MedicinesSideEffect

    @JvmInline
    value class Error(val throwable: Throwable) : MedicinesSideEffect
}
