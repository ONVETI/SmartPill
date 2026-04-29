package uz.onveti.smartpill.screens.add_medicine.state

import uz.onveti.smartpill.data.medicine.MealTiming

internal data class AddMedicineState(
    val isLoading: Boolean = false,
    val name: String = "",
    val morningTime: String = "",
    val afternoonTime: String = "",
    val eveningTime: String = "",
    val mealTiming: MealTiming = MealTiming.AFTER_MEAL,
    val validationMessage: String? = null,
)
