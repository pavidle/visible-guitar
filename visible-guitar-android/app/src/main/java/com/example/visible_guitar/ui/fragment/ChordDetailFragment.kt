package com.example.visible_guitar.ui.fragment

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.visible_guitar.R
import com.example.visible_guitar.common.extensions.navigateToDetailScreen
import com.example.visible_guitar.common.extensions.setAlphaAnimation
import com.example.visible_guitar.common.extensions.setBottomBehavior
import com.example.visible_guitar.common.extensions.showBar
import com.example.visible_guitar.common.util.getRandomColorRGB
import com.example.visible_guitar.databinding.FragmentChordDetailBinding
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.model.states.StateOfList
import com.example.visible_guitar.ui.activity.ChordCameraActivity
import com.example.visible_guitar.ui.adapter.createAdapterOf
import com.example.visible_guitar.ui.adapter.holder.ChordViewHolder
import com.example.visible_guitar.viewmodel.ChordDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.pow

@AndroidEntryPoint
class ChordDetailFragment : BaseFragment<ChordDetailViewModel>(R.layout.fragment_chord_detail) {

    override val viewModel by viewModels<ChordDetailViewModel>()
    private val viewBinding by viewBinding<FragmentChordDetailBinding>()
    private val args by navArgs<ChordDetailFragmentArgs>()

    private val chordAdapter by lazy {
        createAdapterOf(
            layout = R.layout.recyclerview_chords_item_another,
            viewHolder = ::ChordViewHolder,
            onItemClickListener = navigateToDetailScreen { id ->
                ChordDetailFragmentDirections.actionFragmentChordDetailSelf(id)
            }
        )
    }

    override fun setupRequests() {
        viewModel.loadChord(args.chordId)
        viewModel.getOtherChords(args.chordId)
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

        setupRecyclerView(
            recyclerView = viewBinding.recyclerView,
            adapter = chordAdapter,
            orientation = LinearLayoutManager.VERTICAL
        )
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

        viewModel.chordsState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is StateOfList.Error -> Log.e("ERROR", "ERROR")
                is StateOfList.Loading -> Log.e("LOADING", "LOADING")
                is StateOfList.Success -> chordAdapter.submitList(state.data)
            }
        }

    }

}