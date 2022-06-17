package com.example.visible_guitar.ui.fragment.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.common.AuthResult
import com.example.visible_guitar.R
import com.example.visible_guitar.common.extensions.afterTextChanged
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.common.extensions.showBar
import com.example.visible_guitar.databinding.FragmentSignInBinding
import com.example.visible_guitar.model.event.UserSignInFieldsEvent
import com.example.visible_guitar.ui.fragment.BaseFragment
import com.example.visible_guitar.viewmodel.auth.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInViewModel>(R.layout.fragment_sign_in) {

    override val viewModel by viewModels<SignInViewModel>()
    private val viewBinding by viewBinding<FragmentSignInBinding>()

    override fun setupListeners() {
        viewBinding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.email.afterTextChanged { email ->
            viewModel.onSignInEvent(UserSignInFieldsEvent.EmailChanged(email.toString()))
        }

        viewBinding.password.afterTextChanged { password ->
            viewModel.onSignInEvent(UserSignInFieldsEvent.PasswordChanged(password.toString()))
        }
        viewBinding.login.setOnClickListener {
            viewModel.onSignInEvent(UserSignInFieldsEvent.SignIn)
        }
    }

    override fun setupObservers() {
        launchCoroutine {
            viewModel.signInState.collect { result ->
                when(result) {
                    is AuthResult.Authorized -> {
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.nav_graph_auth, inclusive = true)
                            .build()
                        viewBinding.progressBar.visibility = View.INVISIBLE
                        findNavController().navigate(SignUpFragmentDirections.actionGlobalNavGraphHome(), navOptions)
                    }
                    is AuthResult.Loading -> {
                        viewBinding.progressBar.visibility = View.VISIBLE
                    }
                    is AuthResult.Unauthorized -> {
                        viewBinding.progressBar.visibility = View.INVISIBLE
                        result.message?.let { showBar(viewBinding.fragmentSignIn, it) }
                    }
                }
            }
        }
    }
}