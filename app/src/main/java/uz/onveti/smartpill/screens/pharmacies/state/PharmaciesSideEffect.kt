package uz.onveti.smartpill.screens.pharmacies.state

import kotlin.jvm.JvmInline

internal sealed interface PharmaciesSideEffect {

    data object NavigateBack : PharmaciesSideEffect

    @JvmInline
    value class Error(val throwable: Throwable) : PharmaciesSideEffect
}
