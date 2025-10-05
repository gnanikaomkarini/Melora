# Project: Melora - A Personal Music Player for iOS

## 1. Vision & Purpose

This project aims to develop a personal, ad-free music streaming application for iOS. The primary goal is educational: to gain hands-on experience in mobile application development, web scraping, building media conversion tools, and local database management. This application is intended strictly for personal use and will not be distributed.

## 2. Target Platform

The application will be developed exclusively for the iOS platform, targeting a personal iPhone device.

## 3. Core Features

The application will implement the following key features to provide a robust music listening experience.

### 3.1. Audio Playback & System Integration

-   **Background Audio:** The app will continue to play audio when it is in the background or when the device is locked.
-   **Lock Screen & Control Center Integration:** Users will be able to control music playback (play, pause, skip forward/backward) directly from the iOS lock screen and Control Center. Album art and track information should also be visible.
-   **Standard Playback Controls:** The UI will feature standard controls including a play/pause button, next and previous track buttons, and a seekable progress bar.

### 3.2. Music Sourcing & Processing

-   **Web Scraping for Music:** When a user searches for a song, the application will perform a web search to find a corresponding YouTube video link.
-   **Local YouTube to MP3 Conversion:** The application will include a custom-built, local tool to convert the audio from the scraped YouTube link into an MP3 file.
-   **On-Demand Downloading & Caching:** When a user plays a song for the first time, the application will perform the scrape and conversion, then save the resulting MP3 file and its associated metadata (title, artist, album, album art - which may need to be scraped separately or manually entered).
-   **Local Database:** The downloaded metadata will be stored in a local on-device database. The converted MP3 file will be stored in the app's local file storage.
-   **Offline Availability:** Once a song is converted and saved, it will be available for offline playback, with all data being fetched from the local database and storage.

### 3.3. Playlist Management

-   **Create & Manage Playlists:** Users can create new playlists, give them custom names, and delete them.
-   **Add & Remove Songs:** Users will be able to add any song (once saved locally) to one or more playlists. They will also be able to remove songs from a playlist.
-   **View Playlists:** A dedicated section of the app will display all created playlists and the songs within them.

### 3.4. Search Functionality

-   **Web-based Song Discovery:** A primary search feature will initiate the web scraping process to find and convert new songs.
-   **Offline (Library) Search:** A secondary search will allow users to find songs that have already been saved to their local library.

## 4. User Interface & User Experience (UI/UX)

-   **Spotify-like Design:** The user interface will closely mimic the look and feel of the official Spotify application.
-   **Dark Theme:** The application will feature a dark theme as its primary and only interface style.
-   **Now Playing View:** A visually appealing screen that displays the current track's information (album art, title, artist), playback controls, and progress.
-   **Tab-based Navigation:** A simple navigation structure (e.g., Home, Search, Your Library) to allow for easy discovery and access to features.

## 5. Future Enhancements (Optional)

-   **Queue Management:** Ability to see and reorder the upcoming songs in the playback queue.
-   **Advanced Audio Features:** Implementation of an audio equalizer, crossfade functionality, etc.
-   **Lyrics Display:** The ability to show synchronized lyrics for the currently playing song.