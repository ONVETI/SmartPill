package uz.onveti.smartpill.screens.sos.state

import kotlin.jvm.JvmInline

internal sealed interface SosSideEffect {

    data object NavigateBack : SosSideEffect

    @JvmInline
    value class Error(val throwable: Throwable) : SosSideEffect
}
