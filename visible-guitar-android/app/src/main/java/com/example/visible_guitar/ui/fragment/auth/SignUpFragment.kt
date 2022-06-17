package com.example.visible_guitar.ui.fragment.auth

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.common.SignUpResult
import com.example.visible_guitar.R
import com.example.visible_guitar.common.extensions.afterTextChanged
import com.example.visible_guitar.common.extensions.disableFieldError
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.databinding.FragmentSignUpBinding
import com.example.visible_guitar.model.event.UserSignUpFieldsEvent
import com.example.visible_guitar.ui.fragment.BaseFragment
import com.example.visible_guitar.viewmodel.auth.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpViewModel>(R.layout.fragment_sign_up) {

    private val viewBinding by viewBinding<FragmentSignUpBinding>()
    override val viewModel by viewModels<SignUpViewModel>()

    override fun setupListeners() {
        viewBinding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.username.afterTextChanged { username ->
            viewModel.onSignUpEvent(UserSignUpFieldsEvent.UsernameChanged(username.toString()))
        }
        viewBinding.email.afterTextChanged { email ->
            viewModel.onSignUpEvent(UserSignUpFieldsEvent.EmailChanged(email.toString()))
        }
        viewBinding.password.afterTextChanged { password ->
            viewModel.onSignUpEvent(UserSignUpFieldsEvent.PasswordChanged(password.toString()))
        }
        viewBinding.login.setOnClickListener {
            disableFieldError(viewBinding.usernameLayout)
            disableFieldError(viewBinding.emailLayout)
            disableFieldError(viewBinding.passwordLayout)
            viewModel.onSignUpEvent(UserSignUpFieldsEvent.SignUp)
        }

    }

    override fun setupObservers() {
        launchCoroutine {
            viewModel.signUpState.collect { result ->
                when(result) {
                    is SignUpResult.ApiError -> {
                        result.body?.let { body ->
                            body.usernameFieldErrors.onEach { error -> viewBinding.usernameLayout.error = error }
                            body.emailFieldErrors.onEach { error -> viewBinding.emailLayout.error = error }
                            body.passwordFieldErrors.onEach { error -> viewBinding.passwordLayout.error = error }
                        }
                        viewBinding.progressBar.visibility = View.INVISIBLE
                    }
                    is SignUpResult.Loading -> viewBinding.progressBar.visibility = View.VISIBLE
                    is SignUpResult.Registered -> {
                        viewBinding.progressBar.visibility = View.INVISIBLE
                        findNavController().navigate(
                            SignUpFragmentDirections.actionFragmentSignUpToFragmentLogin()
                        )
                    }
                    is SignUpResult.NetworkError -> TODO()
                }
            }
        }

    }
}