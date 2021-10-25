package com.example.data.mapper

import com.example.data.models.SongEntity
import com.example.domain.models.Song
import javax.inject.Inject

class SongMapper @Inject constructor() : Mapper<SongEntity, Song> {
    override fun mapFromEntity(t: SongEntity): Song =
        Song(
            name = t.name,
            accords = t.accords
        )


    override fun mapToEntity(t: Song): SongEntity =
        SongEntity(
            name = t.name,
            accords = t.accords
        )
}