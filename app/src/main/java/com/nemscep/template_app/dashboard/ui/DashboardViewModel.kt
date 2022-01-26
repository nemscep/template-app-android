package com.nemscep.template_app.dashboard.ui

import androidx.lifecycle.ViewModel
import com.nemscep.template_app.auth.domain.usecases.IsUserLoggedIn

class DashboardViewModel(
    private val isUserLoggedIn: IsUserLoggedIn
) : ViewModel() {

    fun isLoggedIn(): Boolean = isUserLoggedIn.single()
}
