package com.nemscep.template_app.dashboard.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.IntegerRes
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.nemscep.template_app.MainGraphDirections
import com.nemscep.template_app.R
import com.nemscep.template_app.auth.ui.AuthFragment
import com.nemscep.template_app.common.ui.viewBinding
import com.nemscep.template_app.databinding.FragmentDashboardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val binding by viewBinding(FragmentDashboardBinding::bind)

    private val viewModel by viewModel<DashboardViewModel>()

    private val navController: NavController
        get() = run {
            (childFragmentManager.findFragmentById(R.id.fcv_dashboard_host) as NavHostFragment)
                .navController
        }

    private val mainNavController: NavController
        get() = run { requireActivity().findNavController(R.id.fcv_main_host) }

    private val deepLinkUrl
        get() = run {
            val args by navArgs<DashboardFragmentArgs>()
            args.deepLinkUrl
        }

    private val onDestinationChangeListener: NavController.OnDestinationChangedListener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            // TODO()
        }

    // TODO("Add login required destination ids")
    private val loginRequiredDestinations: List<Int> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup navigation
        setupNavigation()

        // Setup result listeners
        setupResultListeners()

        // Handle deep link if present.
        handleDeepLink(deepLinkUrl)
    }

    private fun handleDeepLink(deepLinkUrl: String?) {
        // If deep link is not present, do nothing.
        if (deepLinkUrl == null) return

        arguments?.remove("deep_link_url")

        // Construct deep link request from passed url.
        val deepLinkRequest = NavDeepLinkRequest.Builder
            .fromUri(deepLinkUrl.toUri())
            .build()

        // Match created deepLinkRequest to a specific NavDestination.
        // Check if dashboard nav graph can handle the deep link.
        navController.graph.matchDeepLink(deepLinkRequest)
            ?.destination
            ?.let { destination ->
                val navOptions = navOptions {
                    popUpTo(destination.id) {
                        inclusive = true
                    }
                }
                when {
                    // If deep link destination requires session, request session before navigating.
                    destination.id in loginRequiredDestinations && !viewModel.isLoggedIn() -> {
                        navigateToAuth(destinationUrl = deepLinkUrl)
                    }
                    else -> {
                        navController.navigate(
                            request = deepLinkRequest,
                            navOptions = navOptions
                        )
                    }
                }
            }
    }

    override fun onDestroyView() {
        navController.removeOnDestinationChangedListener(onDestinationChangeListener)
        super.onDestroyView()
    }

    private fun setupNavigation() {
        // Configure nav controller.
        navController.addOnDestinationChangedListener(onDestinationChangeListener)
        // Configure bottom navigation view.
        binding.bnvDashboard.setupWithNavController(navController)
        binding.bnvDashboard.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId in loginRequiredDestinations && !viewModel.isLoggedIn()) {
                // Show login.
                // TODO("Navigate to auth and pass destination deep link uri")
//                navigateToAuth(menuItem.itemId.deepLinkUrl)
                return@setOnItemSelectedListener false
            }
            // Connect bottom navigation change listener with graph destinations.
            NavigationUI.onNavDestinationSelected(menuItem, navController)

            // Handle specific bottom navigation clicks if needed.
            //
            return@setOnItemSelectedListener true
        }
    }

    private fun setupResultListeners() {
        val navController = findNavController()
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<AuthFragment.AuthResult>(AuthFragment.RESULT_KEY)
            ?.observe(viewLifecycleOwner) { authenticationResult ->
                when (authenticationResult) {
                    AuthFragment.AuthResult.Cancelled -> navController.popBackStack()
                    is AuthFragment.AuthResult.Success -> {
                        if (authenticationResult.destinationUrl == null) return@observe
                        navController.navigate(
                            NavDeepLinkRequest.Builder
                                .fromUri(authenticationResult.destinationUrl.toUri())
                                .build()
                        )
                    }
                }
            }
    }

    private fun navigateToAuth(destinationUrl: String) {
        // TODO("Navigate to auth")

        mainNavController.navigate(
            NavDeepLinkRequest.Builder
                .fromUri("app://auth/$destinationUrl".toUri())
                .build()
        )
    }
}
