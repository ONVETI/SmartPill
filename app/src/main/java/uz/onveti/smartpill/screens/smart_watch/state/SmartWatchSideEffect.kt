package uz.onveti.smartpill.screens.smart_watch.state

import kotlin.jvm.JvmInline

internal sealed interface SmartWatchSideEffect {

    data object NavigateBack : SmartWatchSideEffect

    @JvmInline
    value class Error(val throwable: Throwable) : SmartWatchSideEffect
}
