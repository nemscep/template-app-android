package com.nemscep.template_app.auth.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.nemscep.template_app.R
import com.nemscep.template_app.common.ui.viewBinding
import com.nemscep.template_app.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel by sharedViewModel<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO
    }
}
