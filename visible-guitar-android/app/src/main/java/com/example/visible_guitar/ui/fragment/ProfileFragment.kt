package com.example.visible_guitar.ui.fragment

import android.view.View
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.visible_guitar.R
import com.example.visible_guitar.common.extensions.showBar
import com.example.visible_guitar.common.util.getRandomColorRGB
import com.example.visible_guitar.databinding.FragmentProfileBinding
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.model.states.StateOfList
import com.example.visible_guitar.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewModel>(R.layout.fragment_profile) {
    override val viewModel by viewModels<ProfileViewModel>()
    private val viewBinding by viewBinding<FragmentProfileBinding>()


    override fun setupObservers() = with(viewBinding) {
        viewModel.currentUserState.observe(viewLifecycleOwner) { state ->
            viewBinding.progressBar.isVisible = state is State.Loading
            viewBinding.exit.isVisible = state !is State.Loading
            when (state) {
                is State.Error -> showBar(fragmentProfile, state.error)
                is State.Loading -> {
                    viewBinding.progressBar.visibility = View.VISIBLE
                    viewBinding.exit.visibility = View.VISIBLE
                }
                is State.Success -> {
                    name.text = state.data.username
                    chordsCount.text = context?.getString(R.string.chordsCount, state.data.chords.size.toString())
                    melodiesCount.text = context?.getString(R.string.melodiesCount, state.data.melodies.size.toString())
                }
            }
        }
    }



}