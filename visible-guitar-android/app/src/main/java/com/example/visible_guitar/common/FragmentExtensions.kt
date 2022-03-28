package com.example.visible_guitar.common

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.visible_guitar.R
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.ui.activity.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun Fragment.launchCoroutine(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        block()
    }
}

internal fun Fragment.navigateToChordDetailScreen(chord: Chord) {
    val intent = Intent(context, MainActivity::class.java)
    intent.putExtra("chord_id", chord.id)
    startActivity(intent)
//    findNavController().navigate(
//        R.id.action_homeFragment_to_chordDetailFragment,
//        bundleOf(ChordDetailFragment.CHORD_ID to chord.id)
//    )
}

internal fun Fragment.showBar(view: View, message: String) {
    val bar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    bar
        .setBackgroundTint(ContextCompat.getColor(context!!, R.color.dark_red))
        .setDuration(8000)
        .show()
}

