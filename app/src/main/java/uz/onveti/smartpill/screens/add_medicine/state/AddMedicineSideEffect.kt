package uz.onveti.smartpill.screens.add_medicine.state

import kotlin.jvm.JvmInline

internal sealed interface AddMedicineSideEffect {

    data object NavigateBack : AddMedicineSideEffect

    data object Saved : AddMedicineSideEffect

    @JvmInline
    value class Error(val throwable: Throwable) : AddMedicineSideEffect
}
