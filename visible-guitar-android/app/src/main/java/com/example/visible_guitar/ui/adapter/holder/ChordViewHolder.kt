package com.example.visible_guitar.ui.adapter.holder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.example.visible_guitar.R
import com.example.visible_guitar.common.util.getRandomColorRGB
import com.example.visible_guitar.model.Chord

class ChordViewHolder(private val view: View) : RecyclerViewHolder<Chord>(view) {

    override fun bind(item: Chord) {
        val itemName = view.findViewById<AppCompatTextView>(R.id.itemName)
        val instrumentName = view.findViewById<AppCompatTextView>(R.id.instrumentName)
//        val layout = view.findViewById<LinearLayout>(R.id.layoutItem)
        itemName.text = item.name
//        layout.setBackgroundColor(getRandomColorRGB(20, 160))
        instrumentName.text = view.context.getString(R.string.instrument, item.instrument.name)
        instrumentName.setBackgroundColor(getRandomColorRGB(20, 160))
    }
}