package com.nemscep.template_app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import com.nemscep.template_app.common.ui.viewBinding
import com.nemscep.template_app.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val navController
        get() = run {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fcv_main_host) as NavHostFragment
            navHostFragment.navController
        }

    private val viewModel by viewModel<MainActivityViewModel>()

    private val onDestinationChangeListener: NavController.OnDestinationChangedListener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            // TODO()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Install splash screen
        val splashScreen = installSplashScreen()

        // Set layout
        setContentView(binding.root)

        // Setup navigation.
        setupNavigation()
    }

    private fun setupNavigation() {
        // Setup destination change listener.
        navController.addOnDestinationChangedListener(onDestinationChangeListener)

        // Depending on data part of the intent, if there is an uri present, handle deep link logic.
        // If deep link uri is present set it as argument to the start destination.
        // Also, set intent to null in order to invalidate implicit deep link handle with the provided one.
        val deepLinkUrl = intent?.data?.toString()
        if (deepLinkUrl != null) intent = null

        navController.setGraph(
            graphResId = R.navigation.main_graph,
            startDestinationArgs = Bundle().apply {
                putString("deep_link_url", deepLinkUrl)
            }
        )
    }

    override fun onDestroy() {
        navController.removeOnDestinationChangedListener(onDestinationChangeListener)
        super.onDestroy()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleNewIntent(intent)
    }

    // Handle deep links
    private fun handleNewIntent(intent: Intent?) {
        when {
            navController.handleDeepLink(intent) -> Unit
            else -> {
                // Handle deep link for specific destination.
                val deepLinkUrl = intent?.data?.toString()
                when {
                    deepLinkUrl == null -> Unit // Do nothing.
                    // TODO()
                }
            }
        }
    }
}
