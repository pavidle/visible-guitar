package com.example.visible_guitar.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.visible_guitar.R
import com.example.visible_guitar.databinding.FragmentChordDetailBinding


class ChordDetailFragment : Fragment() {

    companion object {
        const val CHORD_ID = "chord_id"
    }

    private var _binding: FragmentChordDetailBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChordDetailBinding.inflate(inflater, container, false)
        val id = arguments?.get(CHORD_ID)
        Log.e("id", id.toString())
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

}