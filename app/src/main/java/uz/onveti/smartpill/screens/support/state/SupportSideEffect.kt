package uz.onveti.smartpill.screens.support.state

import kotlin.jvm.JvmInline

internal sealed interface SupportSideEffect {

    data object NavigateBack : SupportSideEffect

    @JvmInline
    value class Error(val throwable: Throwable) : SupportSideEffect
}
