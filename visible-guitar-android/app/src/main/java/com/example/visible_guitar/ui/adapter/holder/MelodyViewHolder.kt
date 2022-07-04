package com.example.visible_guitar.ui.adapter.holder

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.example.visible_guitar.R
import com.example.visible_guitar.common.util.getRandomColorRGB
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.Melody

class MelodyViewHolder(private val view: View) : RecyclerViewHolder<Melody>(view) {

    override fun bind(item: Melody) {
        val author = view.findViewById<AppCompatTextView>(R.id.author)
        val melody = view.findViewById<AppCompatTextView>(R.id.melodyName)
        val instrumentName = view.findViewById<AppCompatTextView>(R.id.melodyInstrumentName)

        author.text = item.author.name
        melody.text = item.name
        instrumentName.text = item.chords[0].instrument.name
        val color = getRandomColorRGB(20, 160)

        author.setBackgroundColor(color)
        melody.setBackgroundColor(color)
    }
}