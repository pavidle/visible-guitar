package com.example.visible_guitar.ui.fragment

import android.content.Intent
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.visible_guitar.R
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.common.extensions.setAlphaAnimation
import com.example.visible_guitar.common.extensions.setBottomBehavior
import com.example.visible_guitar.common.extensions.showBar
import com.example.visible_guitar.databinding.FragmentChordDetailBinding
import com.example.visible_guitar.model.states.DetailState
import com.example.visible_guitar.ui.activity.CameraActivity
import com.example.visible_guitar.viewmodel.ChordDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.pow

@AndroidEntryPoint
class ChordDetailFragment : BaseFragment<ChordDetailViewModel>(R.layout.fragment_chord_detail) {

    companion object {
        const val CHORD_ID = "chord_id"
    }

    override val viewModel by viewModels<ChordDetailViewModel>()
    private val viewBinding by viewBinding<FragmentChordDetailBinding>()


    override fun setupRequests() {
        val id = arguments?.get(CHORD_ID)
        launchCoroutine {
            viewModel.loadChord(id as Int)
        }
    }


    override fun setupViews() {
        setBottomBehavior(viewBinding.sheet) { slideOffset ->
            setAlphaAnimation(viewBinding.backgroundImage, 0.7f - slideOffset)
            setAlphaAnimation(viewBinding.chordName, 0.7f - slideOffset)
            setAlphaAnimation(
                viewBinding.chordNameCollapse,
                slideOffset.toDouble().pow(4.0).toFloat()
            )
        }
    }

    override fun setupObservers() {
        viewBinding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.play.setOnClickListener {
            val intent = Intent(context, CameraActivity::class.java)
            intent.putExtra(CameraActivity.ID, arguments?.get(CHORD_ID) as Int)
            startActivity(intent)
        }
        viewModel.chordState.observe(viewLifecycleOwner, { state ->
            viewBinding.progressBar.isVisible = state is DetailState.Loading

            when(state) {
                is DetailState.Error -> showBar(viewBinding.fragmentChordDetail, state.error)
                is DetailState.Loading -> viewBinding.progressBar.visibility = View.VISIBLE
                is DetailState.Success -> {
                    viewBinding.chordName.text = state.data.name
                    viewBinding.chordNameCollapse.text = state.data.name
                }
            }
        })
    }

}