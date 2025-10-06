# 5000: Project Structure

This document outlines the proposed project structure for Melora. The structure is designed to be clean, modular, and scalable, following modern Android development best practices. It emphasizes a separation of concerns, making the codebase easier to understand, maintain, and test.

## High-Level Overview

The project will be organized into a multi-layered architecture with a feature-based package structure.

```
com.melora
├── data
│   ├── local
│   │   ├── AppDatabase.kt
│   │   ├── dao
│   │   │   └── SongDao.kt
│   │   └── entity
│   │       └── SongEntity.kt
│   ├── remote
│   │   ├── dto
│   │   │   └── SearchResultDto.kt
│   │   └── service
│   │       └── YoutubeService.kt
│   └── repository
│       ├── SongRepository.kt
│       └── SongRepositoryImpl.kt
├── di
│   ├── AppModule.kt
│   └── DatabaseModule.kt
├── domain
│   ├── model
│   │   └── Song.kt
│   └── usecase
│       ├── GetSongUseCase.kt
│       ├── DownloadSongUseCase.kt
│       └── SearchSongUseCase.kt
├── ui
│   ├── features
│   │   ├── player
│   │   │   ├── PlayerScreen.kt
│   │   │   └── PlayerViewModel.kt
│   │   ├── playlist
│   │   │   ├── PlaylistScreen.kt
│   │   │   └── PlaylistViewModel.kt
│   │   └── search
│   │       ├── SearchScreen.kt
│   │       └── SearchViewModel.kt
│   ├── theme
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Typography.kt
│   └── components
│       ├── SongItem.kt
│       └── SearchBar.kt
└── utils
    ├── Constants.kt
    └── extensions
        └── StringExtensions.kt
```

## Layer Explanations

### `data`

The data layer is responsible for providing data to the application. It contains data sources (local and remote) and repositories.

-   **`local`**: This package contains all the components related to the local database (Room).
    -   `AppDatabase.kt`: The main Room database class.
    -   `dao`: Data Access Objects for interacting with the database.
    -   `entity`: Data entities that represent tables in the database.
-   **`remote`**: This package handles all network-related operations.
    -   `dto`: Data Transfer Objects used for parsing network responses.
    -   `service`: Network service interfaces (e.g., for OkHttp/Retrofit).
-   **`repository`**: This package contains the repository implementations. Repositories are responsible for coordinating data from different data sources (local and remote) and providing a clean API for the domain layer.

### `di`

This package will contain all the dependency injection modules (e.g., using Hilt or Koin). This helps in providing dependencies to different parts of the application in a clean and decoupled way.

### `domain`

The domain layer contains the core business logic of the application. It is a pure Kotlin module and should not have any dependencies on the Android framework.

-   **`model`**: Contains the domain models that represent the core business objects of the application.
-   **`usecase`**: Contains the use cases (or interactors) that encapsulate a single piece of business logic.

### `ui`

The UI layer is responsible for displaying the application's user interface. It is built using Jetpack Compose.

-   **`features`**: This is the main package for the UI. It is organized by features (e.g., `search`, `player`, `playlist`). Each feature package contains the screen, ViewModel, and any other UI-related components specific to that feature.
-   **`theme`**: Contains the app-wide theme, colors, and typography.
-   **`components`**: Contains shared UI components that can be reused across multiple features.

### `utils`

This package contains utility classes and extension functions that can be used throughout the application.
