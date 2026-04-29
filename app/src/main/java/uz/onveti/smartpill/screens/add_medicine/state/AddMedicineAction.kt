package uz.onveti.smartpill.screens.add_medicine.state

import uz.onveti.smartpill.data.medicine.MealTiming

internal sealed interface AddMedicineAction {

    data object NavigateBack : AddMedicineAction

    data class NameChanged(val value: String) : AddMedicineAction

    data class MorningTimeChanged(val value: String) : AddMedicineAction

    data class AfternoonTimeChanged(val value: String) : AddMedicineAction

    data class EveningTimeChanged(val value: String) : AddMedicineAction

    data class MealTimingChanged(val value: MealTiming) : AddMedicineAction

    data object SaveClicked : AddMedicineAction

}
