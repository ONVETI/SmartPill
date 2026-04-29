package uz.onveti.smartpill.screens.medicine_detail.state

internal sealed interface MedicineDetailAction {

    data object NavigateBack : MedicineDetailAction

    data class LoadMedicine(val medicineId: String) : MedicineDetailAction

}
