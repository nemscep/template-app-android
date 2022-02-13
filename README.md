# Template app - Android
## Summary
This repository consists of basic android app project setup which includes the following baselines:
- [Dependencies](#dependencies)
- [Build Features](#build-features)
- [App structure](#app-structure)


### Dependencies
- [Koin](https://insert-koin.io/docs/quickstart/kotlin)
- [Jetpack Navigation](https://developer.android.com/guide/navigation)
- [Activity Result API](https://developer.android.com/training/basics/intents/result)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- [Data Store](https://developer.android.com/jetpack/androidx/releases/datastore)
- [Jetpack Compose](https://developer.android.com/jetpack/compose/setup)
- [Lifecycle, LiveData, ViewModel](https://developer.android.com/jetpack/androidx/releases/lifecycle)
- [Moshi](https://github.com/square/moshi)
- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://square.github.io/okhttp/)
- [Splash Screen API](https://developer.android.com/guide/topics/ui/splash-screen)

Tests:
- [Mockito](https://developer.android.com/training/testing/local-tests)
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)
- [Kluent](https://github.com/MarkusAmshove/Kluent)

### Build features

- [Android View Binding](https://developer.android.com/topic/libraries/view-binding)
- [Jetpack Compose](https://developer.android.com/jetpack/compose/setup)
- [Safe Args](https://developer.android.com/guide/navigation/navigation-pass-data)

### App structure
#### Architecture
Chosen architecture for the project is single activity with MVVM and Clean approach.\
From the unidirectional data flow POV, starting from the ui, this means the following:\
- Fragment/Activity/Composable
- ViewModel
- UseCase
- Repository
- DataSource/s

#### Startup flow
In order to get things easily started, simple UI flow structure is implemented with empty screens.\
This includes the following app start flow:
- Splash
- Login (if required)
- Dashboard (bottom navigation screen)

#### Deep links
Deep links are handled inside the `MainActivity`'s `onNewIntent` and `onCreate` which result\
in propagating required uri to all 3 levels described above.
