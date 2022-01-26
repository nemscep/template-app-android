package com.nemscep.template_app.auth.domain.usecases

import com.nemscep.template_app.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Use case responsible for returning logged in state.
 */
class IsUserLoggedIn(private val authRepository: AuthRepository) {
    fun flow(): Flow<Boolean> = flowOf(true)
    fun single(): Boolean = true
}
