package com.example.visible_guitar.ui.fragment

import android.content.Intent
import android.view.View
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
import com.example.visible_guitar.databinding.FragmentChordDetailBinding
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.ui.activity.ChordCameraActivity
import com.example.visible_guitar.viewmodel.ChordDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.pow

@AndroidEntryPoint
class ChordDetailFragment : BaseFragment<ChordDetailViewModel>(R.layout.fragment_chord_detail) {

    override val viewModel by viewModels<ChordDetailViewModel>()
    private val viewBinding by viewBinding<FragmentChordDetailBinding>()
    private val args by navArgs<ChordDetailFragmentArgs>()

    override fun setupRequests() {
        viewModel.loadChord(args.chordId)
    }

    override fun setupViews() {
        setBottomBehavior(viewBinding.sheet) { slideOffset ->
            setAlphaAnimation(viewBinding.backgroundImage, 0.7f - slideOffset)
            setAlphaAnimation(viewBinding.chordName, 0.7f - slideOffset)
            setAlphaAnimation(viewBinding.begin, 1 - slideOffset)
            setAlphaAnimation(
                viewBinding.chordNameCollapse,
                slideOffset.toDouble().pow(4.0).toFloat()
            )
        }
    }

    override fun setupListeners() = with(viewBinding) {
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        play.setOnClickListener {
            val intent = Intent(context, ChordCameraActivity::class.java)
            intent.putExtra(ChordCameraActivity.ID, args.chordId)
            startActivity(intent)
        }
    }

    override fun setupObservers() = with(viewBinding) {
        viewModel.chordState.observe(viewLifecycleOwner) { state ->
            progressBar.isVisible = state is State.Loading

            when (state) {
                is State.Error -> showBar(fragmentChordDetail, state.error)
                is State.Loading -> progressBar.visibility = View.VISIBLE
                is State.Success -> {
                    chordName.text = state.data.name
                    chordNameCollapse.text = state.data.name
                    chordNameCollapse.setBackgroundColor(getRandomColorRGB(20, 160))
                }
            }
        }
    }

}