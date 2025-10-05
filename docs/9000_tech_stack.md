# 9000: Melora Tech Stack

## 1. Introduction

This document outlines the complete technology stack for the Melora iOS application. For each component of the project, we will compare the available options and select the most appropriate one for our specific use case: a modern, educational, and personal iOS project.

Finally, this document will provide a comprehensive guide on how to set up the development environment, create the initial project, and deploy the application to a personal iPhone.

## 2. Core Components & Technology Choices

The application can be broken down into several key components. Here is a comparison of the technologies for each.

### 2.1. iOS Application Framework

This is the foundation of our app. The choice here determines how we build the entire user interface and manage the application's lifecycle.

| Option | Pros | Cons | Verdict |
| :--- | :--- | :--- | :--- |
| **SwiftUI** | Modern, declarative syntax. Faster development. Deep integration with new iOS features and SwiftData. Backed by Apple for future development. | Less mature than UIKit, some advanced features might require a workaround. | **Chosen**. For a new personal and educational project, SwiftUI is the definitive choice. It represents the future of iOS development and allows for rapid UI creation. |
| **UIKit** | Extremely mature and stable. Vast amount of resources and third-party libraries available. | Imperative and often verbose. Slower UI development compared to SwiftUI. Being superseded by SwiftUI for new projects. | **Rejected**. While powerful, UIKit is unnecessarily complex for a new project where learning modern practices is a primary goal. |

### 2.2. Local Database

We need a database to store the metadata for the songs we download (title, artist, path to the local MP3 file, etc.).

| Option | Pros | Cons | Verdict |
| :--- | :--- | :--- | :--- |
| **SwiftData** | Brand new, designed for SwiftUI. Extremely simple and requires minimal boilerplate. Built on top of the robust Core Data stack. | Very new (iOS 17+), fewer resources online. | **Chosen**. As the latest and most integrated database framework for SwiftUI, SwiftData is the perfect choice for this project. It aligns with the goal of learning modern, iOS-specific technologies. |
| **Core Data** | Apple's native, powerful, and mature object graph management framework. | Steep learning curve. Can be verbose and complex to set up compared to newer alternatives. | **Rejected**. SwiftData provides a much simpler, modern API on top of Core Data, making it the superior choice. |
| **Realm** | Very fast, easy to use, and popular third-party solution. | Adds an external dependency. Not as seamlessly integrated with SwiftUI as SwiftData. | **Rejected**. While a great option, SwiftData is the more "native" and forward-looking choice for an iOS-only project. |

### 2.3. Web Scraping

To find the YouTube link for a song, we need to perform a web search and parse the resulting HTML.

| Option | Pros | Cons | Verdict |
| :--- | :--- | :--- | :--- |
| **SwiftSoup** | A pure Swift library for working with real-world HTML. Provides a simple API for extracting data using CSS selectors. | Adds an external dependency. | **Chosen**. This is the ideal tool for the job. It simplifies the complex and error-prone task of manually parsing HTML, allowing us to focus on the app's logic. |
| **Manual Parsing** | No external dependencies. | Extremely brittle and difficult to maintain. A small change in the target website's HTML can break the entire process. | **Rejected**. This approach is not scalable or reliable. |

### 2.4. YouTube to MP3 Conversion

This is the most complex part of the project. The user specified a "youtube to mp3 converter(built by us)". Building this from scratch on-device is not feasible due to technical complexity and the constant changes in YouTube's infrastructure. The most practical "built by us" solution is a small, local server that the app communicates with.

| Option | Pros | Cons | Verdict |
| :--- | :--- | :--- | :--- |
| **Local Server + yt-dlp** | **Reliable and robust.** `yt-dlp` is a well-maintained tool specifically for this purpose. The server can run on your Mac. This fulfills the "built by us" requirement in a practical way. | Requires running a separate process (the server) on your computer while using the app. Adds a component outside the iOS app itself. | **Chosen**. This is the most realistic and effective approach. It provides a stable backend for the conversion process that you control. |
| **On-Device Library** | Everything is contained within the app. | No reliable, modern Swift libraries exist for this. This approach is highly prone to breaking and would be a significant project in itself. | **Rejected**. The technical challenge is too high and unreliable for the scope of this project. |

### 2.5. Networking

To perform the web scraping, we need to make HTTP requests.

| Option | Pros | Cons | Verdict |
| :--- | :--- | :--- | :--- |
| **URLSession** | Apple's native networking framework. Powerful, built-in, and requires no external dependencies. | Can be slightly more verbose than third-party wrappers. | **Chosen**. For the simple GET requests we need to make, URLSession is more than sufficient and avoids adding unnecessary dependencies. |
| **Alamofire** | A very popular and powerful third-party networking library for Swift. Simplifies many common networking tasks. | Adds an external dependency for a task that can be handled natively. | **Rejected**. Overkill for our needs. |

## 3. Summary of Chosen Tech Stack

| Component | Technology |
| :--- | :--- |
| **iOS Framework** | SwiftUI |
| **Database** | SwiftData |
| **Web Scraping** | SwiftSoup |
| **YT to MP3 Conversion** | Local Python Server (Flask/FastAPI + yt-dlp) |
| **Networking** | URLSession |

---

## 4. Project Setup and Installation Guide

This guide will walk you through setting up the Xcode project, installing dependencies, and running the app on your iPhone.

### Step 1: Prerequisites

1.  **Mac:** A Mac computer running the latest version of macOS.
2.  **Xcode:** Install the latest version of Xcode from the Mac App Store.
3.  **Apple ID:** You will need an Apple ID. A free account is sufficient to run the app on your personal device.
4.  **Homebrew:** A package manager for macOS. If you don't have it, open Terminal and run:
    ```bash
    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
    ```

### Step 2: Setup the Local Conversion Server

We will use the lightweight Flask framework in Python to create a server that takes a YouTube URL and returns the MP3.

1.  **Install Python & yt-dlp:**
    Open Terminal and run:
    ```bash
    brew install python yt-dlp ffmpeg
    ```
2.  **Install Flask:**
    ```bash
    pip3 install Flask
    ```
3.  **Create the Server File:**
    In your project's `backend` folder, create a file named `server.py` and add the following code:

    ```python
    from flask import Flask, request, send_file
    import yt_dlp
    import os

    app = Flask(__name__)

    @app.route('/convert', methods=['GET'])
    def convert_video():
        video_url = request.args.get('url')
        if not video_url:
            return "No URL provided", 400

        # Configure yt-dlp to download audio only
        ydl_opts = {
            'format': 'bestaudio/best',
            'postprocessors': [{
                'key': 'FFmpegExtractAudio',
                'preferredcodec': 'mp3',
                'preferredquality': '192',
            }],
            'outtmpl': 'downloads/%(id)s.%(ext)s',
        }

        try:
            with yt_dlp.YoutubeDL(ydl_opts) as ydl:
                info_dict = ydl.extract_info(video_url, download=True)
                video_id = info_dict.get("id", None)
                file_path = f'downloads/{video_id}.mp3'
                
                if os.path.exists(file_path):
                    return send_file(file_path, as_attachment=True)
                else:
                    return "Conversion failed", 500

        except Exception as e:
            return str(e), 500

    if __name__ == '__main__':
        if not os.path.exists('downloads'):
            os.makedirs('downloads')
        app.run(host='0.0.0.0', port=8080)
    ```

4.  **Run the Server:**
    Every time you want to use the app, you must first run this server. Open Terminal, navigate to the `backend` folder, and run:
    ```bash
    python3 server.py
    ```
    Keep this terminal window open while you are using the app.

### Step 3: Create the Xcode Project

1.  **Open Xcode** and select "Create a new Xcode project".
2.  Choose the **iOS** platform and select the **App** template. Click Next.
3.  **Product Name:** Enter `Melora`.
4.  **Team:** Select your Apple ID. (If you haven't added it, Xcode will prompt you).
5.  **Organization Identifier:** Enter something unique, like `com.yourname`.
6.  **Interface:** Make sure **SwiftUI** is selected.
7.  **Language:** Make sure **Swift** is selected.
8.  **Storage:** Make sure **SwiftData** is selected.
9.  Click **Next** and choose the root of your `Melora` repository to save the project in a new `Frontend` folder.

### Step 4: Add Dependencies using Swift Package Manager

1.  In Xcode, with your project open, go to **File > Add Packages...**.
2.  In the search bar in the top right, paste the following URL:
    ```
    https://github.com/scinfu/SwiftSoup.git
    ```
3.  Xcode will find the package. Keep the default "Up to Next Major Version" dependency rule and click **Add Package**.
4.  Select the `SwiftSoup` library and add it to the `Melora` target. Click **Add Package**.

### Step 5: Run the App on Your iPhone

1.  **Connect your iPhone** to your Mac using a USB cable.
2.  **Trust the Computer:** Unlock your iPhone and tap "Trust" if an alert appears.
3.  **Select the Device:** In Xcode, at the top of the window, click on the device selector (it might currently say "iPhone 15 Pro" or another simulator). Your iPhone should appear in the list. Select it.
4.  **Trust the Developer:** The first time you run the app, you may need to trust the developer on your iPhone. Go to **Settings > General > VPN & Device Management**, find your Apple ID under the "Developer App" section, and tap "Trust".
5.  **Run the App:** Press the **Run** button (the play icon) in the top-left of the Xcode window. Xcode will build the project and install it on your iPhone.

You are now fully set up to begin developing the Melora app!
