package com.example.visible_guitar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.visible_guitar.R
import com.example.visible_guitar.model.Chord

class ChordAdapter(
    private val onChordSelected: (Chord, View) -> Unit
) : RecyclerView.Adapter<ChordAdapter.ChordViewHolder>() {

    private val chords = mutableListOf<Chord>()

    fun add(chords: List<Chord>) {
        this.chords.addAll(chords)
        notifyItemChanged(this.chords.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChordViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return ChordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChordViewHolder, position: Int) {
        val chord = chords[position]
        holder.bind(chord, onChordSelected)
    }

    override fun getItemCount(): Int = chords.size

    class ChordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(chord: Chord, listener: (Chord, View) -> Unit) = with(itemView) {
            val textView = itemView.findViewById(R.id.textViewLarge) as TextView
            textView.text = chord.name
            setOnClickListener {
                listener(chord, itemView)
            }
        }
    }
}