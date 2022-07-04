package com.example.visible_guitar.ui.fragment

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.visible_guitar.R
import com.example.visible_guitar.common.extensions.navigateToDetailScreen
import com.example.visible_guitar.common.extensions.showBar
import com.example.visible_guitar.databinding.FragmentHomeBinding

import com.example.visible_guitar.model.states.StateOfList
import com.example.visible_guitar.ui.adapter.createAdapterOf
import com.example.visible_guitar.ui.adapter.holder.ChordViewHolder
import com.example.visible_guitar.ui.adapter.holder.MelodyViewHolder
//import com.example.visible_guitar.ui.adapter.holder.MelodyViewHolder
import com.example.visible_guitar.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home) {

    override val viewModel by viewModels<HomeViewModel>()
    private val viewBinding by viewBinding<FragmentHomeBinding>()
    private val chordAdapter by lazy {
        createAdapterOf(
            layout = R.layout.recyclerview_chords_item,
            viewHolder = ::ChordViewHolder,
            onItemClickListener = navigateToDetailScreen { id ->
                HomeFragmentDirections.actionNavigationHomeToChordDetailFragment(id)
            }
        )
    }

    private val melodiesAdapter by lazy {
        createAdapterOf(
            layout = R.layout.recyclerview_songs_item,
            viewHolder = ::MelodyViewHolder,
            onItemClickListener = navigateToDetailScreen { id ->
                HomeFragmentDirections.actionNavigationHomeToMelodyDetailFragment(id)
            }
        )
    }

    override fun setupObservers() {
        viewBinding.reload.setOnClickListener {
            viewModel.reload()
        }

        viewModel.chordsState.observe(viewLifecycleOwner) { state ->
            viewBinding.progressBar.isVisible = state is StateOfList.Loading
            viewBinding.reload.isVisible = state is StateOfList.Error
            when (state) {
                is StateOfList.Loading -> viewBinding.progressBar.visibility = View.VISIBLE
                is StateOfList.Error -> showBar(viewBinding.recyclerViewChords, state.error)
                is StateOfList.Success -> chordAdapter.submitList(state.data)
            }
        }

        viewModel.melodiesState.observe(viewLifecycleOwner) { state ->

            when (state) {
                is StateOfList.Loading -> viewBinding.progressBar.visibility = View.VISIBLE
                is StateOfList.Error -> showBar(viewBinding.recyclerViewMelodies, state.error)
                is StateOfList.Success -> melodiesAdapter.submitList(state.data)
            }
        }
    }

    override fun setupViews() {
        setupRecyclerView(
            recyclerView = viewBinding.recyclerViewChords,
            adapter = chordAdapter,
            orientation = LinearLayoutManager.HORIZONTAL
        )
        setupRecyclerView(
            recyclerView = viewBinding.recyclerViewMelodies,
            adapter = melodiesAdapter,
            orientation = LinearLayoutManager.HORIZONTAL
        )
    }
}