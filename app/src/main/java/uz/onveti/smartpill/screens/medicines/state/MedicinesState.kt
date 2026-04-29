package uz.onveti.smartpill.screens.medicines.state

import uz.onveti.smartpill.data.medicine.Medicine

internal data class MedicinesState(
    val isLoading: Boolean = false,
    val medicines: List<Medicine> = emptyList(),
    val lastPillStatus: String? = null,
)
