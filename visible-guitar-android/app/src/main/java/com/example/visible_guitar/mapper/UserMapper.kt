package com.example.visible_guitar.mapper

import com.example.domain.mapper.Mapper
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
                Melody(
                    melody.id!!,
                    melody.name,
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