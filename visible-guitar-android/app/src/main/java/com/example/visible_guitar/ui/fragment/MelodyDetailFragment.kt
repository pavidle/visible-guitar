package com.example.visible_guitar.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.visible_guitar.R
import com.example.visible_guitar.common.extensions.setAlphaAnimation
import com.example.visible_guitar.common.extensions.setBottomBehavior
import com.example.visible_guitar.common.extensions.showBar
import com.example.visible_guitar.common.util.getRandomColorRGB
import com.example.visible_guitar.databinding.FragmentMelodyDetailBinding
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.ui.activity.ChordCameraActivity
import com.example.visible_guitar.ui.activity.MelodyCameraActivity
import com.example.visible_guitar.viewmodel.MelodyDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.pow

@AndroidEntryPoint
class MelodyDetailFragment : BaseFragment<MelodyDetailViewModel>(R.layout.fragment_melody_detail) {

    override val viewModel by viewModels<MelodyDetailViewModel>()
    private val viewBinding by viewBinding<FragmentMelodyDetailBinding>()
    private val args by navArgs<MelodyDetailFragmentArgs>()

    override fun setupRequests() {
        viewModel.loadMelody(args.melodyId)
    }

    override fun setupListeners() = with(viewBinding) {
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        play.setOnClickListener {
            val intent = Intent(context, MelodyCameraActivity::class.java)
            intent.putExtra(MelodyCameraActivity.ID, args.melodyId)
            startActivity(intent)
        }
    }

    override fun setupViews() = with(viewBinding){
        setBottomBehavior(viewBinding.sheet) { slideOffset ->
            setAlphaAnimation(viewBinding.backgroundImage, 0.7f - slideOffset)
            setAlphaAnimation(viewBinding.melodyName, 0.7f - slideOffset)
            setAlphaAnimation(viewBinding.begin, 1 - slideOffset)
            setAlphaAnimation(
                viewBinding.melodyNameCollapse,
                slideOffset.toDouble().pow(4.0).toFloat()
            )
        }
    }


    override fun setupObservers() = with(viewBinding) {
        viewModel.melodyState.observe(viewLifecycleOwner) { state ->
            progressBar.isVisible = state is State.Loading

            when (state) {
                is State.Error -> showBar(fragmentMelodyDetail, state.error)
                is State.Loading -> progressBar.visibility = View.VISIBLE
                is State.Success -> {
                    (state.data.author.name + " - " + state.data.name).also { melodyName.text = it }
                    (state.data.author.name + " - " + state.data.name).also { melodyNameCollapse.text = it }
                    melodyNameCollapse.setBackgroundColor(getRandomColorRGB(20, 160))
                }
            }
        }
    }

}