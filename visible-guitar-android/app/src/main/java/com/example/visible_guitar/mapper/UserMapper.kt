package com.example.visible_guitar.mapper

import android.util.Log
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.domain.model.InstrumentEntity
import com.example.domain.model.MelodyEntity
import com.example.domain.model.NoteEntity
import com.example.domain.model.auth.UserEntity
import com.example.visible_guitar.model.*

class UserMapper : Mapper<UserEntity, User> {
    override fun convert(type: UserEntity): User {
        return User(
            type.id,
            type.username,
            type.email,
            type.chords.map { chord ->
                Chord(chord.id,
                    Instrument(chord.id, chord.name),
                    chord.name,
                    chord.notes.map { note ->
                        Note(note.id, note.name, note.stringNumber, note.fretNumber)
                    }
                )
            },
            type.melodies.map { melody ->
                Melody(melody.name,
                    Author(melody.author.id, melody.author.name),
                    melody.chords.map { chord ->
                    Chord(chord.id,
                        Instrument(chord.id, chord.name),
                        chord.name,
                        chord.notes.map { note ->
                            Note(note.id, note.name, note.stringNumber, note.fretNumber)
                        }
                    )
                })
            }
        )
    }

}