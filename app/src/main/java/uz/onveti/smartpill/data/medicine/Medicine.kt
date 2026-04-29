package uz.onveti.smartpill.data.medicine

data class Medicine(
    val id: String = "",
    val name: String = "",
    val morningTime: String = "",
    val afternoonTime: String = "",
    val eveningTime: String = "",
    val mealTiming: MealTiming = MealTiming.AFTER_MEAL,
    val createdAt: Long = 0L,
)
