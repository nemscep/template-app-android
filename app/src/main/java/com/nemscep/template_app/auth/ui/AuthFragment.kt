package com.nemscep.template_app.auth.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.nemscep.template_app.R
import com.nemscep.template_app.common.ui.viewBinding
import com.nemscep.template_app.databinding.FragmentAuthBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AuthFragment : Fragment(R.layout.fragment_auth) {
    private val binding by viewBinding(FragmentAuthBinding::bind)

    private val viewModel by sharedViewModel<AuthViewModel>()

    private val destinationUrl: String? by lazy {
        val args by navArgs<AuthFragmentArgs>()
        args.destinationUrl
    }

    private val navController: NavController
        get() = run {
            (childFragmentManager.findFragmentById(R.id.fcv_auth_host) as NavHostFragment)
                .navController
        }

    private val mainNavController: NavController
        get() = run {
            requireActivity().findNavController(R.id.fcv_main_host)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO
    }

    private fun completeAuth(){
        mainNavController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(
                RESULT_KEY,
                AuthResult.Success(destinationUrl = destinationUrl)
            )

        mainNavController.popBackStack(R.id.authFragment, true)
    }

    private fun cancelAuth(){
        mainNavController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(
                RESULT_KEY,
                AuthResult.Cancelled
            )

        mainNavController.popBackStack(R.id.authFragment, true)
    }

    sealed class AuthResult {
        data class Success(val destinationUrl: String?) : AuthResult()
        object Cancelled : AuthResult()
    }

    companion object {
        const val RESULT_KEY = "auth-result"
    }
}
