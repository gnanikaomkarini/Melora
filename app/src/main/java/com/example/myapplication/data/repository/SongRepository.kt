package com.example.myapplication.data.repository

import com.example.myapplication.domain.model.Song

interface SongRepository {
    suspend fun search(query: String): List<Song>
}
