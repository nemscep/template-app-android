package com.nemscep.template_app

import com.nemscep.template_app.auth.data.AuthRepositoryImpl
import com.nemscep.template_app.auth.domain.repository.AuthRepository
import com.nemscep.template_app.auth.domain.usecases.IsUserLoggedIn
import com.nemscep.template_app.splash.SplashViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * All koin dependencies.
 */
val modules = listOf(
    // Common
    module {
        single {
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        }

        single {
            HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
        }

        single {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor(logger = get()))
                .build()
        }

        single {
            Retrofit.Builder()
                .baseUrl("/")
                .addConverterFactory(MoshiConverterFactory.create(get()))
                .client(get())
                .build()
        }
    },
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
