package com.example.visible_guitar.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.visible_guitar.ui.activity.CameraActivity
import com.example.visible_guitar.R
import com.example.visible_guitar.databinding.FragmentHomeBinding
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.ui.adapter.ChordAdapter
import com.example.visible_guitar.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val chordAdapter = ChordAdapter {
        chord, _ ->
        navigateToDetailChordScreen(chord)
    }

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun navigateToDetailChordScreen(chord: Chord) {
        val intent = Intent(context, CameraActivity::class.java)
        intent.putExtra("chord_id", chord.id)
        startActivity(intent)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel.state.observe ( this, {
            it?.items?.let { chords -> chordAdapter.add(chords) }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = chordAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}