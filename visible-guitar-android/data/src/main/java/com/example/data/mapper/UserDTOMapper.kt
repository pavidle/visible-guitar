package com.example.data.mapper

import android.util.Log
import com.example.data.model.auth.UserDTO
import com.example.domain.mapper.Mapper
import com.example.domain.model.*
import com.example.domain.model.auth.UserEntity

class UserDTOMapper : Mapper<UserDTO, UserEntity> {
    override fun convert(type: UserDTO): UserEntity {
        return UserEntity(
            type.id,
            type.username,
            type.email,
            type.chords.map { chord ->
                ChordEntity(chord.id,
                InstrumentEntity(chord.id, chord.name),
                    chord.name,
                    chord.notes.map { note ->
                        NoteEntity(note.id, note.name, note.stringNumber, note.fretNumber)
                    }
                )
            },
            type.melodies.map { melody ->
                MelodyEntity(melody.name,
                    AuthorEntity(melody.author.id, melody.author.name),
                    melody.chords.map { chord ->
                    ChordEntity(chord.id,
                        InstrumentEntity(chord.id, chord.name),
                        chord.name,
                        chord.notes.map { note ->
                            NoteEntity(note.id, note.name, note.stringNumber, note.fretNumber)
                        }
                    )
                })
            }
        )
    }

}