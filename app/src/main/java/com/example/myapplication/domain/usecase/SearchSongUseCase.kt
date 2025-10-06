package com.example.myapplication.domain.usecase

import com.example.myapplication.data.repository.SongRepository
import com.example.myapplication.domain.model.Song

class SearchSongUseCase(private val songRepository: SongRepository) {
    suspend operator fun invoke(query: String): List<Song> {
        return songRepository.search(query)
    }
}
