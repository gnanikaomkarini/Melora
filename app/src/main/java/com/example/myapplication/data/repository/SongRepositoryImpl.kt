package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.service.YoutubeService
import com.example.myapplication.domain.model.Song

class SongRepositoryImpl(private val youtubeService: YoutubeService) : SongRepository {
    override suspend fun search(query: String): List<Song> {
        return youtubeService.search(query)
    }
}
