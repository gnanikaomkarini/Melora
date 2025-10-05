# 9000: Melora Tech Stack (Cross-Platform Revision)

## 1. Introduction

This document outlines a completely revised technology stack for the Melora application. The previous stack was based on the assumption of using a macOS development environment. **Given the constraints of a Linux-based development environment and the requirement to build for iOS, we must switch to a cross-platform framework.**

This revised guide details a new stack, compares the relevant options, and provides a realistic setup and deployment guide for building an iOS app from a Linux machine.

## 2. Core Components & Technology Choices

### 2.1. Application Framework

We need a framework that allows us to write code on Linux but deploy to iOS. This is the most critical choice.

| Option | Pros | Cons | Verdict |
| :--- | :--- | :--- | :--- |
| **Flutter (Dart)** | Excellent for building beautiful, high-performance, natively compiled applications for mobile, web, and desktop from a single codebase. Strong tooling on Linux. Backed by Google. | Uses the Dart language, which may be new to you. | **Chosen**. Flutter is the ideal choice. It provides a complete SDK for building and deploying apps, has a fantastic UI toolkit perfect for a Spotify-like interface, and is well-supported on Linux. |
| **React Native (JS/TS)** | Great for developers with a web background (React). Large community and ecosystem. | Can sometimes have performance issues compared to Flutter. Less of an all-in-one solution. | **Rejected**. While a strong contender, Flutter's all-inclusive nature and UI capabilities give it the edge for this specific project. |

### 2.2. Local Database

To store song metadata locally on the phone.

| Option | Pros | Cons | Verdict |
| :--- | :--- | :--- | :--- |
| **Hive** | A very fast, lightweight, and easy-to-use key-value database written in pure Dart. Perfect for simple data storage. | Not a full-fledged SQL database, less suitable for complex queries. | **Chosen**. For storing song metadata, Hive is simple, efficient, and a favorite in the Flutter community. It's perfect for our needs. |
| **Drift (Moor)** | A reactive persistence library built on SQLite. Powerful, type-safe, and allows for complex SQL queries. | More complex to set up and use than Hive. | **Rejected**. Overkill for this project's requirements. |

### 2.3. Web Scraping

To parse web pages to find YouTube links.

| Option | Pros | Cons | Verdict |
| :--- | :--- | :--- | :--- |
| **http + html (Dart Packages)** | The standard, recommended combination in the Dart/Flutter ecosystem for making network requests and parsing HTML. | Requires combining two packages. | **Chosen**. This is the standard, idiomatic way to handle this task in Dart. |

### 2.4. YouTube to MP3 Conversion

This is the most critical feature. The goal is to have this functionality self-contained within the app, without needing a separate server running on a computer.

| Option | Pros | Cons | Verdict |
| :--- | :--- | :--- | :--- |
| **youtube_explode_dart** | A pure Dart library that reverse-engineers YouTube's internal API to get direct stream links for audio. This allows for in-app conversion without an external server. | Can break temporarily when YouTube updates its internal API. The library maintainers usually fix it quickly. | **Chosen**. This is the best solution that meets the "entirely from my phone" requirement. It keeps the entire process within the app. |
| **Public API** | Use a third-party web service that does the conversion. | Relies on an external service that could shut down, have rate limits, or cost money. | **Rejected**. Less reliable and not self-contained. |

## 3. Summary of Chosen Tech Stack

| Component | Technology |
| :--- | :--- |
| **Framework** | Flutter (using Dart language) |
| **Database** | Hive |
| **Web Scraping** | `http` + `html` packages |
| **YT Conversion** | `youtube_explode_dart` package |
| **State Management** | Flutter BLoC (Recommended for structuring the app) |

---

## 4. Project Setup and Installation Guide

### CRITICAL: Building for iOS from Linux

It is **not possible** to compile an iOS application directly on a Linux machine. Apple's software (Xcode) is required to create the final app file (`.ipa`), and Xcode only runs on macOS. 

**The solution is to use a cloud-based CI/CD (Continuous Integration/Continuous Deployment) service.** You will do all your development and testing on Linux (using an Android emulator or a physical Android device), and then use a cloud service that provides a macOS environment to build the final iOS app.

**This process also requires a paid Apple Developer Account ($99/year).** This is an unavoidable requirement from Apple to sign and install an app on a physical iPhone.

### Step 1: Prerequisites on Linux

1.  **Install Flutter:** Follow the official guide for your Linux distribution: [Flutter Linux Install Guide](https://docs.flutter.dev/get-started/install/linux).
2.  **Install VS Code:** The recommended editor for Flutter development. Install it from its official website.
3.  **Install Android Studio:** Even if you only target iOS, you need Android Studio for the Android SDK and for running an Android emulator for testing. [Install Android Studio for Linux](https://developer.android.com/studio).
4.  **Run `flutter doctor`:** Open your terminal and run this command. It will tell you what components you are missing. Follow its instructions to install any missing dependencies.

### Step 2: Create the Flutter Project

1.  Open your terminal and navigate to the `Melora` repository.
2.  Create the Flutter project in the `Frontend` directory:
    ```bash
    flutter create Frontend
    ```
3.  Open the `Frontend` folder in VS Code.

### Step 3: Add Dependencies

1.  Open the `pubspec.yaml` file in the root of your new `Frontend` project.
2.  Add the following lines under `dependencies`:

    ```yaml
    dependencies:
      flutter: 
        sdk: flutter
      # Add these packages
      http: ^1.2.1
      html: ^0.15.4
      youtube_explode_dart: ^2.2.0
      hive: ^2.2.3
      hive_flutter: ^1.1.0
      path_provider: ^2.0.11
      flutter_bloc: ^8.1.2 # For state management
    ```
3.  Add the following under `dev_dependencies` for code generation for Hive:
    ```yaml
    dev_dependencies:
      flutter_test:
        sdk: flutter
      # Add these packages
      hive_generator: ^2.0.0
      build_runner: ^2.3.3
    ```
4.  Save the file. VS Code should automatically run `flutter pub get`. If not, run it in the terminal.

### Step 4: The iOS Build Process (Cloud Build)

This is the final step once your app is developed and tested on Linux.

1.  **Get a Paid Apple Developer Account:** Enroll at [developer.apple.com](https://developer.apple.com/).
2.  **Push Your Code:** Make sure your entire project is pushed to a Git repository (e.g., GitHub, GitLab).
3.  **Use a Cloud Build Service (Codemagic):**
    -   **Sign up for Codemagic.io** with your Git provider account. They have a free tier that is sufficient for this.
    -   **Add your application** and connect it to your Git repository.
    -   **Configure Code Signing:** Codemagic has excellent documentation on how to set up iOS code signing. You will need to generate certificates and a provisioning profile from your Apple Developer account and upload them to Codemagic.
    -   **Start the Build:** Configure a workflow to build the iOS version of your app.
    -   **Download the `.ipa` file:** Once the build is complete, Codemagic will provide a downloadable `.ipa` file.
4.  **Install the App on Your iPhone:**
    -   The easiest way is to use **Apple TestFlight**. You can upload your `.ipa` file to App Store Connect (part of your developer account) and invite yourself as a tester. You can then download the app via the TestFlight app on your iPhone.

This revised plan is a realistic path forward to achieving your goal from a Linux machine.