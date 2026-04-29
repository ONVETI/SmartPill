package uz.onveti.smartpill.screens.medicine_detail.state

import kotlin.jvm.JvmInline

internal sealed interface MedicineDetailSideEffect {

    data object NavigateBack : MedicineDetailSideEffect

    @JvmInline
    value class Error(val throwable: Throwable) : MedicineDetailSideEffect
}
