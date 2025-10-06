package com.example.myapplication.data.remote.service

import com.example.myapplication.domain.model.Song

class YoutubeService {
    suspend fun search(query: String): List<Song> {
        // TODO: Implement web scraping logic using Jsoup
        return listOf(
            Song("1", "Song 1", "Artist 1", ""),
            Song("2", "Song 2", "Artist 2", ""),
            Song("3", "Song 3", "Artist 3", "")
        )
    }
}
