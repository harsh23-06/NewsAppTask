# NewsAppTask Documentation

## Overview

NewsAppTask is a mobile application designed to provide users with access to news articles from various sources. The app offers a user-friendly interface where users can discover and read the latest news across different categories.

## Tools and Technologies Used

### Programming Language:
- Kotlin

### Development Environment:
- Android Studio

### Libraries and Frameworks:
- Android Jetpack Components (ViewModel, LiveData, Fragment, etc.)
- Glide: For loading and caching images from URLs.
- Firebase Cloud Messaging: For sending push notifications.

### Design and UI:
- XML: Used for designing layouts and UI elements.
- ConstraintLayout: For creating responsive and flexible UI designs.
- Material Design Components: For consistent UI elements and interactions.

### Data Handling:
- JSON: Data format for storing news articles fetched from the server.
- SharedPreferences: For storing user preferences and settings.

### Networking:
- HTTPURLConnection: For establishing connections and fetching data from the server.
- AsyncTask: For performing asynchronous network operations.

## App Working

1. **Home Screen:** Upon launching the app, users are presented with the HomeFragment, where they can browse through the latest news articles. The HomeFragment displays news articles in both horizontal and vertical RecyclerViews, allowing users to scroll through articles conveniently.

2. **Discover Screen:** Users can navigate to the DiscoverFragment to explore news articles based on different categories or topics. The DiscoverFragment provides options for sorting articles by newness or oldness, allowing users to customize their browsing experience.

3. **News Detail:** When users click on a news article, they are directed to the NewsDetailActivity, where they can read the full article. The activity displays the article's title, content, and image, providing a seamless reading experience.

4. **Internet Connectivity:** The app checks for internet connectivity upon launch. If the device is connected to the internet, the app displays news content. However, if there is no internet connection, the app shows a message prompting the user to connect to the internet.

5. **Push Notifications:** The app utilizes Firebase Cloud Messaging to send push notifications to users about the latest news updates and important announcements.

markdown
Copy code
# Screens

<div style="display: flex; flex-wrap: wrap;">
    <img src="https://github.com/harsh23-06/NewsAppTask/blob/master/app/src/main/res/drawable/screen%201.jpeg" width="45%" style="margin-right: 5%;" />
    <img src="https://github.com/harsh23-06/NewsAppTask/blob/master/app/src/main/res/drawable/screen%202.jpeg" width="45%" style="margin-left: 5%;" />
    <img src="https://github.com/harsh23-06/NewsAppTask/blob/master/app/src/main/res/drawable/screen%203.jpeg" width="45%" style="margin-right: 5%; margin-top: 20px;" />
    <img src="https://github.com/harsh23-06/NewsAppTask/blob/master/app/src/main/res/drawable/screen%204.jpeg" width="45%" style="margin-left: 5%; margin-top: 20px;" />
</div>

## Conclusion

NewsAppTask offers users a convenient and intuitive way to stay informed about current events and trending topics. With its user-friendly interface and seamless navigation, the app provides an engaging news reading experience for users on the go. Powered by modern technologies and frameworks, NewsAppTask is a reliable and efficient solution for accessing news content on mobile devices.
