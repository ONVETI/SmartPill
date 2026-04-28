# SmartPill

SmartPill is a native Android application for the Smart MedBox concept. The app is built around one clear idea:

> Biz dori ichishni eslatmaymiz, biz uni nazorat qilamiz. Smart MedBox AI bemorning real harakatini kuzatadi va davoni tashlash xavfini oldindan aniqlaydi.

The current version focuses on a clean Compose dashboard, separate feature screens, AI assistant prototype flow, medicine monitoring UI, pharmacy list UI, smart watch connection design, support, and SOS actions.

## Project Status

| Item | Status |
| --- | --- |
| Platform | Android |
| UI | Jetpack Compose, Material 3 |
| Architecture | MVI, Orbit-MVI |
| Dependency Injection | Koin |
| Navigation | Jetpack Navigation Compose |
| Language | Kotlin |
| Minimum SDK | 28 |
| Target SDK | 36 |

## Features

### Main Dashboard

The main screen contains six primary modules:

| Module | Description |
| --- | --- |
| Dorilar | Medicine list and patient treatment control UI |
| AI Modul | Local AI assistant prototype for medical questions |
| Aptekalar | Nearby pharmacy list UI |
| Smart Soat | Smart watch connection design |
| Support | Help and service contact screen |
| SOS | Emergency services screen with dialer actions |

At the bottom of the main screen, medical quotes rotate every 5 seconds. The quote block includes Ibn Sino and other medical thinkers.

### Separate Screens

Each dashboard module opens its own screen instead of rendering content below the main dashboard. The feature screens were generated with the project script:

```bash
./create_feature.sh feature_name
```

Current screens:

```text
main
medicines
ai_assistant
pharmacies
smart_watch
support
sos
```

## Tech Stack

SmartPill follows Modern Android Development practices:

| Layer | Technology |
| --- | --- |
| UI | Jetpack Compose |
| Design | Material Design 3 |
| State management | Orbit-MVI |
| DI | Koin |
| Navigation | Navigation Compose with serializable routes |
| Async | Kotlin Coroutines |
| Networking | Retrofit, OkHttp |
| Backend services | Firebase Analytics, Firestore, Realtime Database |
| Build system | Gradle Kotlin DSL |

## Architecture

The project uses feature-oriented MVI screens. Each screen keeps state, actions, and side effects separate.

```text
app/src/main/java/uz/onveti/smartpill/
├── app/
│   └── MyApp.kt
├── core/
│   └── ui/
│       └── SmartPillTopBar.kt
├── di/
│   ├── AppModule.kt
│   ├── DataModule.kt
│   ├── InitKoin.kt
│   ├── NetworkModule.kt
│   └── ScreensModule.kt
├── screens/
│   ├── main/
│   ├── medicines/
│   ├── ai_assistant/
│   ├── pharmacies/
│   ├── smart_watch/
│   ├── support/
│   └── sos/
└── ui/
    └── theme/
```

Typical feature structure:

```text
screens/feature_name/
├── FeatureRoute.kt
├── FeatureScreen.kt
├── FeatureViewModel.kt
├── component/
│   ├── FeatureTopBar.kt
│   └── FeatureBottomBar.kt
└── state/
    ├── FeatureAction.kt
    ├── FeatureSideEffect.kt
    └── FeatureState.kt
```

## Getting Started

### Requirements

Install the following before running the project:

| Tool | Version |
| --- | --- |
| Android Studio | Latest stable |
| JDK | 11 or newer |
| Android SDK | Compile SDK 36 |
| Gradle | Use bundled wrapper |

### Clone

```bash
git clone https://github.com/ONVETI/SmartPill.git
cd SmartPill
```

### Build

```bash
./gradlew :app:compileDebugKotlin
```

### Run Unit Tests

```bash
./gradlew :app:testDebugUnitTest
```

### Open in Android Studio

Open the repository root in Android Studio and run the `app` configuration on an emulator or a physical Android device.

## Git Workflow

Recommended workflow for changes:

```bash
git status
git add .
git commit -m "Describe your change"
git push
```

## Notes

This repository currently contains a functional prototype UI. Some product areas, such as real AI backend integration, real smart watch connection, medicine adherence tracking, and pharmacy data source integration, are ready for future implementation.
