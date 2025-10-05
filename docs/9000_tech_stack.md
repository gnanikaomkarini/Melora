# 9000: Melora Tech Stack (Native Android)

## 1. Introduction

This document outlines the definitive technology stack for the Melora application, targeting **Native Android Development**. This approach aligns with the goal of a simplified development process on a Linux machine and avoids external frameworks and servers as requested.

We will use the modern, Google-recommended toolkit for building Android apps.

## 2. Core Components & Technology Choices

### 2.1. Core Language & Framework

This is the foundation of our app.

| Option | Pros | Cons | Verdict |
| :--- | :--- | :--- | :--- |
| **Kotlin** | **Modern, concise, and null-safe.** It's Google's officially preferred language for Android development, leading to better support and a more pleasant developer experience. | Slight learning curve if you are coming from a different language family. | **Chosen**. Kotlin is the clear and correct choice for any new Android project. |
| **Java** | The original language for Android; very mature with a vast amount of legacy code and examples online. | Verbose, lacks modern language features, and is no longer the focus for new Android development. | **Rejected**. Kotlin has superseded Java for modern Android development. |

### 2.2. User Interface (UI) Toolkit

How we will build the user interface.

| Option | Pros | Cons | Verdict |
| :--- | :--- | :--- | :--- |
| **Jetpack Compose** | **Android's modern, declarative UI toolkit.** Drastically simplifies and accelerates UI development. It's built to work seamlessly with Kotlin. | Newer than the traditional View system, so some very complex, niche UI components might not have a native Compose solution yet. | **Chosen**. For building a new, Spotify-like UI, Compose is perfect. It will allow us to build a beautiful, reactive interface quickly. |
| **Android Views (XML)** | The traditional, imperative UI system. Extremely mature. | Verbose and cumbersome. Requires managing state manually and often leads to more boilerplate code. | **Rejected**. Jetpack Compose is the future and is far better suited for a new project. |

### 2.3. Local Database

To store song metadata on the device.

| Option | Pros | Cons | Verdict |
| :--- | :--- | :--- | :--- |
| **Room** | **Part of Android Jetpack, Google's recommended persistence library.** It's a powerful abstraction layer over SQLite that reduces boilerplate and provides compile-time verification of SQL queries. | Requires some initial setup and understanding of annotations. | **Chosen**. Room is the industry standard for modern Android development. It's robust, well-integrated, and reliable. |
| **SQLite (raw)** | No extra library needed, built into Android. | Requires a massive amount of manual, error-prone boilerplate code for even simple operations. | **Rejected**. Room was created specifically to solve the problems of using raw SQLite. |

### 2.4. Web Scraping & Networking

To find YouTube links and download the web pages.

| Component | Technology | Verdict |
| :--- | :--- | :--- |
| **Networking** | **OkHttp** | **Chosen**. A powerful and widely-used industry-standard HTTP client for Kotlin/Java. It's perfect for our web-scraping needs. |
| **HTML Parsing** | **Jsoup** | **Chosen**. The definitive library for parsing HTML in the Java/Kotlin world. It makes extracting information from web pages simple and reliable. |

### 2.5. YouTube to MP3 Conversion

To extract audio from a YouTube link on the device.

| Option | Pros | Cons | Verdict |
| :--- | :--- | :--- | :--- |
| **Bundled Java/Kotlin Library** | **Keeps the entire process on-device and self-contained.** Libraries exist that port the logic of tools like `yt-dlp` to Java/Kotlin. | Can be less stable than a server-side solution, as it depends on reverse-engineering YouTube's API. | **Chosen**. This is the best approach that fits all your requirements. We will use a library like `youtube-dl-android` or a similar alternative. |

## 3. Summary of Chosen Tech Stack

| Component | Technology |
| :--- | :--- |
| **Language** | Kotlin |
| **UI Toolkit** | Jetpack Compose |
| **Database** | Room Persistence Library |
| **Networking** | OkHttp |
| **HTML Parsing** | Jsoup |
| **YT Conversion** | Bundled Java/Kotlin Library (e.g., `youtube-dl-android`) |
| **Build Tool** | Gradle |

---

## 4. Project Setup and Installation Guide

This path is much simpler and is done entirely on your local Linux machine.

### Step 1: Prerequisites

1.  **Java Development Kit (JDK):** Android Studio requires a JDK. You can install it via your distribution's package manager (e.g., `sudo apt install openjdk-17-jdk`).
2.  **Android Studio:** Download and install the latest version of Android Studio for Linux from the [official Android developer website](https://developer.android.com/studio).

### Step 2: Create the Android Studio Project

1.  **Open Android Studio**.
2.  Click on **"New Project"**.
3.  Select the **"Empty Compose App"** template from the phone and tablet section. Click **Next**.
4.  **Name:** `Melora`
5.  **Package name:** `com.yourname.melora` (or similar)
6.  **Save location:** Choose the `Frontend` directory in your project repository.
7.  **Language:** Make sure **Kotlin** is selected.
8.  **Minimum SDK:** Choose a reasonable API level (e.g., API 26 or higher).
9.  Click **Finish**. Android Studio will create and build the project.

### Step 3: Add Dependencies

1.  In your new project, open the `build.gradle.kts` file for the **app module** (usually `app/build.gradle.kts`).
2.  Add the necessary libraries to the `dependencies` block. It will look something like this:

    ```kotlin
    dependencies {
        // Core Android & Compose dependencies (will be there already)
        implementation("androidx.core:core-ktx:1.13.1")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
        implementation("androidx.activity:activity-compose:1.9.0")
        implementation(platform("androidx.compose:compose-bom:2024.05.00"))
        // ... other compose dependencies

        // Add these for our project
        // Room for database
        implementation("androidx.room:room-runtime:2.6.1")
        ksp("androidx.room:room-compiler:2.6.1") // Annotation processor
        implementation("androidx.room:room-ktx:2.6.1") // Kotlin extensions

        // Networking and Scraping
        implementation("com.squareup.okhttp3:okhttp:4.12.0")
        implementation("org.jsoup:jsoup:1.17.2")

        // YouTube Conversion (Example library)
        implementation("com.github.yausername:youtubedl-android:0.1.8")
    }
    ```
3.  You will also need to add the KSP plugin for Room. In the project-level `build.gradle.kts`, and the app-level one, add the appropriate plugin declarations.
4.  Click the **"Sync Now"** banner that appears in Android Studio to download and add the new dependencies.

### Step 4: Run the App on Your Android Phone

1.  **Enable Developer Options** on your phone: Go to Settings > About phone, and tap on the "Build number" seven times.
2.  **Enable USB Debugging:** In the new "Developer options" menu, find and enable "USB debugging".
3.  **Connect your phone** to your Linux machine with a USB cable.
4.  **Select your device:** In the top toolbar of Android Studio, you should see your phone appear in the device dropdown menu.
5.  **Run the app:** Click the green **Run** button (a play icon). Android Studio will compile, install, and launch the app on your phone.

This setup is fully self-contained on your Linux machine and provides a fast and efficient development workflow.