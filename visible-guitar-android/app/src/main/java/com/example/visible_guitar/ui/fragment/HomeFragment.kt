package com.example.visible_guitar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.visible_guitar.R
import com.example.visible_guitar.common.extensions.navigateToDetailScreen
import com.example.visible_guitar.common.extensions.showBar
import com.example.visible_guitar.databinding.FragmentHomeBinding
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.states.ListState
import com.example.visible_guitar.ui.adapter.RecyclerViewAdapter
import com.example.visible_guitar.ui.adapter.createAdapterOf
import com.example.visible_guitar.ui.adapter.holder.ChordViewHolder
import com.example.visible_guitar.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home) {

    override val viewModel by viewModels<HomeViewModel>()
    private val viewBinding by viewBinding<FragmentHomeBinding>()
    private lateinit var chordAdapter: RecyclerViewAdapter<Chord>

    override fun observeLiveData() {
        viewBinding.reload.setOnClickListener {
            viewModel.reload()
        }
        viewModel.state.observe(viewLifecycleOwner, { state ->
            viewBinding.progressBar.isVisible = state is ListState.Loading
            viewBinding.reload.isVisible = state is ListState.Error
            when(state) {
                is ListState.Success -> chordAdapter.submitList(state.data)
                is ListState.Error -> showBar(viewBinding.recyclerView, state.error)
                is ListState.Loading -> viewBinding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    override fun setupViews() {
        chordAdapter = createAdapterOf(
            layout = R.layout.recyclerview_item,
            viewHolder = ::ChordViewHolder,
            onItemClickListener = navigateToDetailScreen(
                R.id.action_navigation_home_to_chordDetailFragment,
                ChordDetailFragment.CHORD_ID
            )
        )
        setupRecyclerView(
            recyclerView = viewBinding.recyclerView,
            adapter = chordAdapter,
            orientation = LinearLayoutManager.HORIZONTAL
        )
    }
}