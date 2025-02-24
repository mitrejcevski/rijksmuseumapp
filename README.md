# Rijks Museum App
An app that uses the Rijks Museum public API

## Functionality
The app consists of 2 screens:
 - The landing screen where all artworks are loaded in a list
 - The details screen that gets open upon clicking on a list item

## Technology Used
 - Compose UI + AAC ViewModel for the UI layer
 - Coil for image loading
 - Kotlinx coroutines for threading
 - Koin for dependency injection
 - Retrofit + OkHttp for the networking layer
 - JUnit5 + Truth for unit testing