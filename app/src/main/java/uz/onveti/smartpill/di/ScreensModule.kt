package uz.onveti.smartpill.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import uz.onveti.smartpill.screens.ai_assistant.AiAssistantViewModel
import uz.onveti.smartpill.screens.main.MainViewModel
import uz.onveti.smartpill.screens.medicines.MedicinesViewModel
import uz.onveti.smartpill.screens.pharmacies.PharmaciesViewModel
import uz.onveti.smartpill.screens.smart_watch.SmartWatchViewModel
import uz.onveti.smartpill.screens.sos.SosViewModel
import uz.onveti.smartpill.screens.support.SupportViewModel

val screensModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::MedicinesViewModel)
    viewModelOf(::AiAssistantViewModel)
    viewModelOf(::PharmaciesViewModel)
    viewModelOf(::SmartWatchViewModel)
    viewModelOf(::SupportViewModel)
    viewModelOf(::SosViewModel)
}
