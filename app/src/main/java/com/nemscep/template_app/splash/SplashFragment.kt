package com.nemscep.template_app.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nemscep.template_app.MainGraphDirections
import com.nemscep.template_app.R
import com.nemscep.template_app.auth.ui.AuthFragment
import com.nemscep.template_app.common.ui.viewBinding
import com.nemscep.template_app.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val viewModel by viewModel<SplashViewModel>()

    private val deepLinkUrl
        get() = run {
            val args by navArgs<SplashFragmentArgs>()
            args.deepLinkUrl
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup live data.
        setupLiveData()

        // Setup result listeners.
        setupResultListeners()

        // Temp
        handleNavigation(deepLinkUrl = null)
    }

    private fun setupResultListeners() {
        val navController = findNavController()
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<AuthFragment.AuthResult>(AuthFragment.RESULT_KEY)
            ?.observe(viewLifecycleOwner) { authenticationResult ->
                when (authenticationResult) {
                    AuthFragment.AuthResult.Cancelled -> navController.popBackStack()
                    is AuthFragment.AuthResult.Success -> openDashboard(deepLinkUrl = deepLinkUrl)
                }
            }
    }

    private fun setupLiveData() {
        // TODO
    }

    private fun handleNavigation(deepLinkUrl: String?) {
        when (deepLinkUrl) {
            null -> openDashboard(deepLinkUrl)
            // TODO
        }
        arguments?.remove("deep_link_url")
    }

    private fun openDashboard(deepLinkUrl: String?) {
        findNavController().navigate(MainGraphDirections.actionGlobalDashboardFragment(deepLinkUrl = deepLinkUrl))
    }
}
