# Project Melora: Overview & Status

This document provides a clear, up-to-date summary of the Melora project for easy reference.

## 1. Project Goal

The primary goal is to build a personal, ad-free music player application for Android. It is an educational project focused on learning native Android development. The application will source music by finding songs on YouTube, converting them to local MP3 files on-device, and allowing the user to manage and play them.

## 2. Core Design

-   **Platform:** Native Android
-   **UI:** A dark-themed, modern user interface inspired by Spotify, built with Jetpack Compose.
-   **Music Workflow:**
    1.  User searches for a song.
    2.  The app scrapes the web to find a relevant YouTube link.
    3.  An on-device library extracts the audio and saves it as an MP3 in the app's local storage.
    4.  Song metadata is saved in a local database.
    5.  The user can then play the downloaded song, add it to playlists, and search their local library.

## 3. Chosen Technology Stack

| Component | Technology |
| :--- | :--- |
| **Language** | Kotlin |
| **UI Toolkit** | Jetpack Compose |
| **Database** | Room Persistence Library |
| **Networking** | OkHttp |
| **HTML Parsing** | Jsoup |
| **YT Conversion** | Bundled Java/Kotlin Library (e.g., `youtube-dl-android`) |
| **Build Tool** | Gradle |

## 4. Current Status & Next Steps

-   The project has been pivoted from iOS/cross-platform to **Native Android**.
-   The `.gitignore` file has been updated for Android development.
-   The directory structure has been cleaned up to prepare for the project root.

---

### **NEXT ACTION REQUIRED**

**The user needs to create the initial Android project structure.**

1.  **Open Android Studio**.
2.  Select **"New Project"** -> **"Empty Compose App"**.
3.  Set the **"Save location"** to the project root: `/home/gnanika/Downloads/Melora`.
4.  **After the project is created, notify me.** I will then proceed with configuring the Gradle files to add all the necessary dependencies.
