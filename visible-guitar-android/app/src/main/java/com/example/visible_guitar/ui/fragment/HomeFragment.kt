package com.example.visible_guitar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.visible_guitar.common.launchCoroutine
import com.example.visible_guitar.common.navigateToChordDetailScreen
import com.example.visible_guitar.common.showBar
import com.example.visible_guitar.databinding.FragmentHomeBinding
import com.example.visible_guitar.ui.adapter.ChordAdapter
import com.example.visible_guitar.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var chordAdapter: ChordAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel.state.observe ( this, {
            binding.progressBar.visibility = if (it.isLoading) View.VISIBLE else View.GONE
            it?.items?.let { chords -> chordAdapter.add(chords) }
        }
        )
        launchCoroutine {
            subscribeOnShowErrorBar()
        }
        return binding.root
    }

    private suspend fun subscribeOnShowErrorBar() {
        homeViewModel.event.collectLatest { event ->
            when (event) {
                is HomeViewModel.BarEvent.Show -> {
                    showBar(binding.recyclerView, event.message)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        chordAdapter = ChordAdapter {
                chord, _ ->
            navigateToChordDetailScreen(chord)
        }
        binding.recyclerView.adapter = chordAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}