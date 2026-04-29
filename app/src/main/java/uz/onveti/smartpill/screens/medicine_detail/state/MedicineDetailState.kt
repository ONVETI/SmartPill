package uz.onveti.smartpill.screens.medicine_detail.state

import uz.onveti.smartpill.data.medicine.Medicine

internal data class MedicineDetailState(
    val isLoading: Boolean = false,
    val medicine: Medicine? = null,
)
