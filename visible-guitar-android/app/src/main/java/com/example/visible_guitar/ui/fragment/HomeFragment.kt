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
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.ui.adapter.createAdapterOf
import com.example.visible_guitar.ui.adapter.holder.ChordViewHolder
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
            onItemClickListener = navigateToDetailScreen(
                R.id.action_navigation_home_to_chordDetailFragment,
                ChordDetailFragment.CHORD_ID
            )
        )
    }

    override fun setupObservers() {
        viewBinding.reload.setOnClickListener {
            viewModel.reload()
        }
        viewModel.chordsState.observe(viewLifecycleOwner, { state ->
            viewBinding.progressBar.isVisible = state is State.Loading
            viewBinding.reload.isVisible = state is State.Error
            when(state) {
                is State.Loading -> viewBinding.progressBar.visibility = View.VISIBLE
                is State.Error -> showBar(viewBinding.recyclerView, state.error)
                is State.Success -> chordAdapter.submitList(state.data)
            }
        })
    }

    override fun setupViews() {
        setupRecyclerView(
            recyclerView = viewBinding.recyclerView,
            adapter = chordAdapter
        )
    }
}