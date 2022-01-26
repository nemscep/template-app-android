package com.nemscep.template_app

import com.nemscep.template_app.auth.data.AuthRepositoryImpl
import com.nemscep.template_app.auth.domain.repository.AuthRepository
import com.nemscep.template_app.auth.domain.usecases.IsUserLoggedIn
import com.nemscep.template_app.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * All koin dependencies.
 */
val modules = listOf(
    // Main
    module {
        viewModel { MainActivityViewModel() }
    },
    // Splash
    module {
        viewModel { SplashViewModel() }
    },
    // Auth
    module {
        single<AuthRepository> { AuthRepositoryImpl() }
        factory { IsUserLoggedIn(authRepository = get()) }
    },
    module { },
    module { },
    module { },
    module { },
)
