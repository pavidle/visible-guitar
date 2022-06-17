package com.example.visible_guitar.ui.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.visible_guitar.R
import com.example.visible_guitar.common.extensions.setAlphaAnimation
import com.example.visible_guitar.databinding.FragmentAuthBinding


class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val viewBinding by viewBinding<FragmentAuthBinding>()
//    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.login.setOnClickListener {
            findNavController().navigate(
                AuthFragmentDirections.actionAuthFragmentToLoginFragment()
            )
        }
        viewBinding.register.setOnClickListener {
            findNavController().navigate(
                AuthFragmentDirections.actionFragmentAuthToFragmentSignUp()
            )
        }

    }
}